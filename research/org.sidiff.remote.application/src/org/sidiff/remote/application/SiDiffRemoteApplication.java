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
import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.adapters.ListOperationResult;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.application.extraction.ExtractionEngine;
import org.sidiff.remote.application.util.ExtensionUtil;
import org.sidiff.remote.common.ProxyObject;
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
	
	private String user;
	
	private String session_id;
	
	private File user_folder;
	
	private File session_folder;
	
	private ModelIndexer modelIndexer;
	
	private ExtractionEngine extractionEngine;
	
	private UUIDResource last_selected_model;
	
	public SiDiffRemoteApplication(IWorkspace workspace, String user, String session_id) {
		this.workspace = workspace;
		this.session_id = session_id;
		this.user = user;
		String ws_path = this.workspace.getRoot().getLocation().toOSString();
		String	user_path= ws_path + File.separator + this.user;
		String session_path = user_path + File.separator + this.session_id;
		
		this.user_folder = new File(user_path);
		if(!user_folder.exists()) {
			user_folder.mkdir();
		}
		this.session_folder = new File(session_path);
		if(!session_folder.exists()) {
			session_folder.mkdir();
		}
		this.modelIndexer = new ModelIndexer(session_folder);
		
		this.extractionEngine = new ExtractionEngine();
		
		this.modelIndexer.index();;
	}
	
	/**
	 * 
	 * @param session_path
	 * 				session based model path
	 * @param local_model_path
	 * 				project relative local model path
	 * @param elementIds
	 * @return
	 * @throws IOException
	 * @throws UncoveredChangesException
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 * @throws NotInitializedException
	 * @throws ExtendedSlicingCriteriaIntersectionException
	 * @throws CoreException 
	 */
	public File checkoutModel(String session_path, String local_model_path, Set<String> elementIds) throws IOException, UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, CoreException {
		
		String absolute_origin_path = user_folder + File.separator + session_path;
		String absolute_copy_path = session_folder + File.separator + local_model_path;
		FileOperations.copyFile(absolute_origin_path, absolute_copy_path);
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		// save complete model as uuid resource
		completeModel.save(completeModel.getDefaultSaveOptions());
		
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
		emptyModel.save(emptyModel.getDefaultSaveOptions());

	
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();

		UUIDResource slicedModel = UUIDResource.createUUIDResource(slicedModelURI, slicedModelResourceSet);
		Map<EObject, EObject> copies_sliced = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
		slicedModel.getContents().addAll(copies_sliced.values());
		for (EObject origin : copies_sliced.keySet()) {
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies_sliced.get(origin), id);
		}
		slicedModel.save(slicedModel.getDefaultSaveOptions());

		return this.extractionEngine.extract(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel);
	}
	
	public ProxyObject getRequestedModelFile(String session_path){
		File file = this.modelIndexer.getFile(session_path);
		return ProxyUtil.convertFileTree(file, session_id);
	}
	
	/**
	 * 
	 * @param local_model_path
	 * 				project relative local model path
	 * @return
	 */
	public List<ProxyObject> getRequestedModelElements(String local_model_path) {
		
		String absolute_copy_path = session_folder + File.separator + local_model_path;
		
		URI completeModelURI = EMFStorage.pathToUri(absolute_copy_path);
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModelURI).replace(completeModelURI.lastSegment(), "sliced_" + completeModelURI.lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
		List<ProxyObject> proxyObjects = ProxyUtil.convertEMFResource(slicedModel); 
		
		return proxyObjects;
	}
	
	public ListOperationResult listRepository(String url, int port, String path, String user, char[] password) throws RepositoryAdapterException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		return repositoryAdapter.list(url, port, path, user, password);
	}
	
	public CheckoutOperationResult addRepository(String url, int port, String path, String user, char[] password) throws RepositoryAdapterException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		String target = this.session_folder.getPath();
		CheckoutOperationResult checkoutOperationResult = repositoryAdapter.checkout(url, port, path, user, password, target);
		modelIndexer.index();
		return checkoutOperationResult;
		
	}
	
	
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
	 * 
	 * @param localPath
	 * 				project relative local model path
	 * @param elementIds
	 * @return
	 * @throws UncoveredChangesException
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException
	 * @throws NotInitializedException
	 * @throws ExtendedSlicingCriteriaIntersectionException
	 * @throws CoreException 
	 */
	public File updateSubModel(String localPath, Set<String> elementIds) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, CoreException {
		String absolute_copy_path = session_folder + File.separator + localPath;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		URI emptydModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));

		ResourceSet emptyModelResourceSet = new ResourceSetImpl();
		UUIDResource emptyModel = new UUIDResource(emptydModelURI, emptyModelResourceSet);
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
		return this.extractionEngine.update(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel);
		
	}
	
}
