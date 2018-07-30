package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.common.file.FileOperations;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.adapters.ListOperationResult;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.application.extraction.ExtractionEngine;
import org.sidiff.remote.application.util.ExtensionUtil;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.exceptions.AddRepositoryException;
import org.sidiff.remote.common.exceptions.CheckoutSubModelException;
import org.sidiff.remote.common.exceptions.ListRepositoryContentException;
import org.sidiff.remote.common.exceptions.UpdateSubModelException;
import org.sidiff.remote.common.util.ProxyUtil;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplication {

	private IWorkspace workspace;
	
	/**
	 * The username
	 */
	private String user;
	
	/**
	 * The session ID
	 */
	private String session_id;
	
	/**
	 * The user folder
	 */
	private File user_folder;
	
	/**
	 * The session folder
	 */
	private File session_folder;
	
	/**
	 * The sidiff folder containing the files which are checked out
	 */
	private File sidiff_folder;
	
	/**
	 * The {@link ModelIndexer} holding an index of all remote files
	 */
	private ModelIndexer modelIndexer;
	
	/**
	 * The {@link ExtractionEngine} for extracting submodels
	 */
	private ExtractionEngine extractionEngine;
	
	/**
	 * The last selected remote model resource
	 */
	private UUIDResource last_selected_model;
	
	public SiDiffRemoteApplication(IWorkspace workspace, String user, String session_id) {
		this.workspace = workspace;
		this.session_id = session_id;
		this.user = user;
		String ws_path = this.workspace.getRoot().getLocation().toOSString();
		String	user_path= ws_path + File.separator + this.user;
		String session_path = user_path + File.separator + this.session_id;
		String sidiff_path = session_path + File.separator + ".sidiff";
		
		this.user_folder = new File(user_path);
		if(!user_folder.exists()) {
			user_folder.mkdir();
		}
		this.session_folder = new File(session_path);
		if(!session_folder.exists()) {
			session_folder.mkdir();
		}
		
		this.sidiff_folder = new File(sidiff_path);
		if(!sidiff_folder.exists()) {
			sidiff_folder.mkdir();
		}
		
		this.modelIndexer = new ModelIndexer(session_folder);
		this.modelIndexer.index();
		
		this.extractionEngine = new ExtractionEngine();
	}
	
	/**
	 * Extracts a submodel containing all requested model elements
	 * 
	 * @param session_path
	 *            session based path for the model to be extracted
	 * @param local_model_path
	 *            project relative local model path
	 * @param elementIds
	 *            the IDs of the requested model elements
	 * @return a {@link File} containing a submodel with all requested model
	 *         elements
	 * @throws CheckoutSubModelException
	 */
	public File checkoutModel(String session_path, String local_model_path, Set<String> elementIds) throws CheckoutSubModelException {
		
		
		
		String absolute_origin_path = user_folder + File.separator + session_path;
		String absolute_copy_path = sidiff_folder + File.separator + local_model_path;
		try {
			FileOperations.copyFile(absolute_origin_path, absolute_copy_path);
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		// save complete model as uuid resource
		try {
			completeModel.save(completeModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		URI emptyModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));
	
		ResourceSet emptyModelResourceSet = new ResourceSetImpl();

		UUIDResource emptyModel = UUIDResource.createUUIDResource(emptyModelURI, emptyModelResourceSet);
		Map<EObject, EObject> copies_empty = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
		emptyModel.getContents().addAll(copies_empty.values());
		for (EObject origin : copies_empty.keySet()) {
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies_empty.get(origin), id);
			System.out.println(EMFUtil.getXmiId(origin) + " : " + EMFUtil.getXmiId(copies_empty.get(origin)));
		}
		try {
			emptyModel.save(emptyModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}

	
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();

		UUIDResource slicedModel = UUIDResource.createUUIDResource(slicedModelURI, slicedModelResourceSet);
		Map<EObject, EObject> copies_sliced = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
		slicedModel.getContents().addAll(copies_sliced.values());
		for (EObject origin : copies_sliced.keySet()) {
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies_sliced.get(origin), id);
		}
		try {
			slicedModel.save(slicedModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}

		File subModelFile = null;
		try {
			subModelFile = this.extractionEngine.extract(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | IOException
				| CoreException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		return subModelFile;
	}
	
	/**
	 * Returns the file tree as {@link ProxyObject} for the file given by the session path
	 * 
	 * @param session_path
	 *            session based path for the requested file
	 * @return the file tree as {@link ProxyObject} for the file given by the session path
	 */
	public ProxyObject getRequestedModelFile(String session_path){
		File file = this.modelIndexer.getFile(session_path);
		return ProxyUtil.convertFileTree(file, session_id);
	}
	
	/**
	 * Returns a model element tree as {@link ProxyObject}s for the requested model elements
	 * 
	 * @param local_model_path
	 * 				project relative local model path
	 * @return a model element tree as {@link ProxyObject}s for the requested model elements
	 */
	public List<ProxyObject> getRequestedModelElements(String local_model_path) {
		
		String absolute_copy_path = sidiff_folder + File.separator + local_model_path;
		
		URI completeModelURI = EMFStorage.pathToUri(absolute_copy_path);
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModelURI).replace(completeModelURI.lastSegment(), "sliced_" + completeModelURI.lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
		List<ProxyObject> proxyObjects = ProxyUtil.convertEMFResource(slicedModel); 
		
		return proxyObjects;
	}
	
	/**
	 * Lists the repository content based on the given path
	 * 
	 * @param url
	 * 			the URL of the repository to be listed
	 * @param port
	 * 			the port of the repository to be listed
	 * @param path
	 * 			a relative path of the repository content to be listed
	 * @param user
	 * 			the username for accessing the repository
	 * @param password
	 * 			the password for accessing the repository
	 * 
	 * @return a {@link ListOperationResult}
	 * @throws ListRepositoryContentException
	 */
	public ListOperationResult listRepository(String url, int port, String path, String user, char[] password) throws ListRepositoryContentException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		ListOperationResult listOperationResult = null;
		try {
			listOperationResult = repositoryAdapter.list(url, port, path, user, password);
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new ListRepositoryContentException(e);
		}
		return listOperationResult;
	}
	
	/**
	 * Adds the repository to the remote application and checks out the files given by the path 
	 * @param url
	 * 			the URL of the repository to be added to the remote application
	 * @param port
	 * 			the port of the repository to be added to the remote application
	 * @param path
	 * 			a relative path of the repository content to be copied to the remote application
	 * @param user
	 * 			the username for accessing the repository
	 * @param password
	 * 			the password for accessing the repository
	 * @return a {@link CheckoutOperationResult}
	 * @throws AddRepositoryException 
	 */
	public CheckoutOperationResult addRepository(String url, int port, String path, String user, char[] password) throws AddRepositoryException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		String target = this.session_folder.getPath();
		CheckoutOperationResult checkoutOperationResult = null;
		try {
			checkoutOperationResult = repositoryAdapter.checkout(url, port, path, user, password, target);
			modelIndexer.index();
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new AddRepositoryException(e);
		}
		
		return checkoutOperationResult;
	}
	
	/**
	 * Browses the repository files based on the given path and IDs
	 * 
	 * @param session_path
	 *            session based path for the parent file to be browsed
	 * @param elementID
	 * 			element ID of the model element to be browsed
	 * @return a {@link List} of {@link ProxyObject}s containing the browsed content
	 */
	public List<ProxyObject> browse(String session_path, String elementID) {

		File file = this.modelIndexer.getFile(session_path);
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		if (file.isDirectory()) {
			for (File child : this.modelIndexer.getChildren(file)) {
				ProxyObject proxyObject = ProxyUtil.convertFile(child, this.session_id);
				proxyObjects.add(proxyObject);
			}
		} else {
			proxyObjects.addAll(browseModel(file, elementID));
		}

		return proxyObjects;
	}	
	
	
	private List<ProxyObject> browseModel(File file, String elementID) {
		String absolute_path = file.getAbsolutePath();
		URI uri = EMFStorage.pathToUri(absolute_path);
		if(last_selected_model == null || !last_selected_model.getURI().toString().equals(uri.toString())) {
			ResourceSet resourceSet = new ResourceSetImpl();
			last_selected_model = new UUIDResource(EMFStorage.pathToUri(absolute_path), resourceSet);
			try {
				last_selected_model.save(last_selected_model.getDefaultSaveOptions());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<ProxyObject> content = new ArrayList<ProxyObject>();
		if(elementID == null) {
			for(EObject eObject : last_selected_model.getContents()) {
				ProxyObject proxyObject = ProxyUtil.convertEObject(eObject);
				content.add(proxyObject);
			}
		}else {
			
			EObject eObject = last_selected_model.getIDToEObjectMap().get(elementID);
				
			for (EObject eObj : eObject.eContents()) {
				ProxyObject proxyObject = ProxyUtil.convertEObject(eObj);
				content.add(proxyObject);
			}
			
		}
		return content;
	}
	
	/**
	 * Returns a slicing edit script for updating the local submodel
	 * @param localPath
	 * 				project relative local model path
	 * @param elementIds
	 * 				the requested model element IDs
	 * @return a slicing edit script for updating the local submodel
	 * @throws UpdateSubModelException 

	 */
	public File updateSubModel(String localPath, Set<String> elementIds) throws UpdateSubModelException  {
		String absolute_copy_path = session_folder + File.separator + localPath;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		URI emptydModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));

		ResourceSet emptyModelResourceSet = new ResourceSetImpl();
		UUIDResource emptyModel = new UUIDResource(emptydModelURI, emptyModelResourceSet);
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
		File slicingEditScriptFile = null;
		try {
			slicingEditScriptFile = this.extractionEngine.update(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | CoreException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new UpdateSubModelException(e);
		}
		
		return slicingEditScriptFile;
	}
	
}
