package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.common.exceptions.SiDiffRuntimeException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.adapters.InfoOperationResult;
import org.sidiff.remote.application.adapters.ListOperationResult;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.application.extraction.ExtractionEngine;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.exceptions.AddRepositoryException;
import org.sidiff.remote.common.exceptions.CheckoutSubModelException;
import org.sidiff.remote.common.exceptions.GetRequestedModelElementsException;
import org.sidiff.remote.common.exceptions.ListRepositoryContentException;
import org.sidiff.remote.common.exceptions.UpdateSubModelException;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.util.ProxyUtil;
import org.sidiff.slicer.rulebased.exceptions.EmptySlicingCriteriaException;
import org.sidiff.slicer.rulebased.exceptions.EmtpyModelSliceException;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;
import org.sidiff.slicer.rulebased.util.UUIDResourceUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplication {
	
	/**
	 * 
	 */
	private static final String SIDIFF_INF = ".SiDiff";

	/**
	 * The workspace of the {@link SiDiffRemoteApplication}
	 */
	private IWorkspace workspace;
	
	/**
	 * The user folder
	 */
	private File user_folder;
	
	/**
	 * The {@link ModelIndexer} building an index over all remote files
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
	
	/**
	 * 
	 * @param workspace
	 * @param user
	 */
	public SiDiffRemoteApplication(IWorkspace workspace, String user) {
		
		this.workspace = workspace;
		
		String ws_path = this.workspace.getRoot().getLocation().toOSString();
		
		String	user_path= ws_path + File.separator + user;
		
		this.user_folder = new File(user_path);
		if(!user_folder.exists()) {
			user_folder.mkdir();
		}
		
		this.modelIndexer = new ModelIndexer(user_folder);
		this.modelIndexer.index();
		
		this.extractionEngine = new ExtractionEngine();
	}
	
	/**
	 * browses the remote repository content
	 * 
	 * @param url
	 *            the URL of the repository, i.e. protocol + domain + name
	 * @param port
	 *            the port of the repository
	 * @param path
	 *            an absolute path within the repository
	 * @param user
	 *            the user name for accessing the repository content
	 * @param password
	 *            the password for accessing the repository content
	 * @return the content of the repository location as {@link List}
	 * @throws ListRepositoryContentException
	 */
	public ListOperationResult browseRepositoryContent(String url, int port, String path, String user, char[] password) throws ListRepositoryContentException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter =
				IRepositoryAdapter.MANAGER.getExtension("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter")
					.orElseThrow(() -> new SiDiffRuntimeException("repository adapter not found"));

		ListOperationResult infoOperationResult = null;
		try {
			infoOperationResult = repositoryAdapter.list(url, port, path, user, password);
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new ListRepositoryContentException(e);
		}
		return infoOperationResult;
	}
	
	/**
	 * Browses the remote application content. If the requested file is a folder,
	 * its content is returned. If the requested file is a model file and no
	 * elementID is given, the root elements of the model are returned. If an
	 * elementID is given, the elements contained by the identified element are
	 * returned.
	 * 
	 * @param relative_remote_file_path
	 *            path of a requested remote file relative to the current user
	 *            directory
	 * @param elementID
	 *            the ID of a requested model element (only used if the requested
	 *            file is a model file)
	 * @param infinite
	 *            flag for gathering contained elements of the requested model element (only used if
	 *            the requested file is a model file)
	 * @return the content of the remote application as {@link List} of
	 *         {@link ProxyObject}s
	 */
	public List<ProxyObject> browseRemoteApplicationContent(String relative_remote_file_path, String elementID, boolean infinite) {

		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter =
				IRepositoryAdapter.MANAGER.getExtension("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter")
					.orElseThrow(() -> new SiDiffRuntimeException("repository adapter not found"));
				
		File file = this.modelIndexer.getFile(relative_remote_file_path);
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		if (file.isDirectory()) {
			for (File child : this.modelIndexer.getChildren(file)) {
				ProxyObject proxyObject = ProxyUtil.convertFile(child, user_folder);
				proxyObjects.add(proxyObject);
				try {
					InfoOperationResult inforOperationResult = repositoryAdapter.info(child);
					
					ProxyUtil.addProperty(proxyObject, "URL", inforOperationResult.getUrl());
					ProxyUtil.addProperty(proxyObject, "Port", String.valueOf(inforOperationResult.getPort()));
					ProxyUtil.addProperty(proxyObject, "Path", inforOperationResult.getPath());
					ProxyUtil.addProperty(proxyObject, "Revision", inforOperationResult.getRevision());
					ProxyUtil.addProperty(proxyObject, "Author", inforOperationResult.getAuthor());
					
				} catch (RepositoryAdapterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			String absolute_file_path = file.getAbsolutePath();
			URI uri = EMFStorage.pathToUri(absolute_file_path);
			if(last_selected_model == null || !last_selected_model.getURI().toString().equals(uri.toString())) {
				try {
					ResourceSet resourceSet = new ResourceSetImpl();
					last_selected_model = new UUIDResource(EMFStorage.pathToUri(absolute_file_path), resourceSet);
					last_selected_model.save(last_selected_model.getDefaultSaveOptions());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(elementID == null) {
				for(EObject eObject : last_selected_model.getContents()) {
					ProxyObject proxyObject = ProxyUtil.convertEObject(eObject, infinite);
					proxyObjects.add(proxyObject);
				}
			}else {
				
				EObject eObject = last_selected_model.getIDToEObjectMap().get(elementID);
					
				for (EObject eObj : eObject.eContents()) {
					ProxyObject proxyObject = ProxyUtil.convertEObject(eObj, infinite);
					proxyObjects.add(proxyObject);
				}
				
			}
		}

		return proxyObjects;
	}	
	

	/**
	 * 
	 * Extracts a submodel containing at least all elements identified by the elementIDs.
	 * 
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user directory
	 * @param relative_local_model_path
	 *            path of the local model file relative to the local workspace
	 * @param elementIds
	 *            the IDs of the model elements to be checked out.
	 * @param preferences
	 * @return a {@link File} containing a submodel with all requested model
	 *         elements
	 * @throws CheckoutSubModelException
	 */
	public File checkoutModel(String relative_remote_model_path, String relative_local_model_path, Set<String> elementIds, RemotePreferences preferences) throws CheckoutSubModelException {
		
		String absolute_origin_path = user_folder.getAbsolutePath() + File.separator + relative_remote_model_path;
		String sidiff_inf_path = new Path(absolute_origin_path).removeLastSegments(1).toOSString() + File.separator + SIDIFF_INF;
		String absolute_copy_path = sidiff_inf_path + File.separator + relative_local_model_path;
		
		File sidiff_inf_file = new File(sidiff_inf_path);
		
		// remove existing difference files
		if(sidiff_inf_file.exists()) {
			for(File file : modelIndexer.searchDifferenceFiles(sidiff_inf_file).values()) {
				file.delete();
			}
		}
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource sessionModel;
		try {
			sessionModel = new UUIDResource(EMFStorage.pathToUri(absolute_origin_path), resourceSet);
		} catch (IOException e) {
			throw new CheckoutSubModelException(e);
		}
		
		UUIDResource completeModel = UUIDResourceUtil.copyResource(sessionModel, EMFStorage.pathToUri(absolute_copy_path));
		File subModelFile = null;
		
		try {
			// save complete model as uuid resource
			completeModel.save(completeModel.getDefaultSaveOptions());

			URI emptyModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI())
					.replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));

			UUIDResource emptyModel = UUIDResourceUtil.copyMinimalResource(completeModel, emptyModelURI);

			// save empty model as uuid resource
			emptyModel.save(emptyModel.getDefaultSaveOptions());

			URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI())
					.replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

			UUIDResource slicedModel = UUIDResourceUtil.copyResource(emptyModel, slicedModelURI);
			
			// save sliced model as uuid resource
			slicedModel.save(slicedModel.getDefaultSaveOptions());

			subModelFile = this.extractionEngine.extract(new HashSet<String>(elementIds), completeModel, emptyModel,
					slicedModel, preferences);

			// TODO determine right repository adapter
			IRepositoryAdapter repositoryAdapter =
					IRepositoryAdapter.MANAGER.getExtension("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter")
						.orElseThrow(() -> new SiDiffRuntimeException("repository adapter not found"));

			InfoOperationResult infoOperationResult;

			infoOperationResult = repositoryAdapter.info(new File(absolute_origin_path).getParentFile());
			repositoryAdapter.commit(infoOperationResult.getUrl(), infoOperationResult.getPort(),
					infoOperationResult.getPath(), sidiff_inf_file, null, null, "<commit>");
			
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | IOException | RepositoryAdapterException | EmptySlicingCriteriaException | EmtpyModelSliceException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		return subModelFile;
	}
	
	/**
	 * Returns all folder and files which are reachable by any step of the relative
	 * remote model path as hierarchically structured proxy objects
	 * 
	 * @param relative_remote_model_path
	 *            path for the requested remote model file relative to the current
	 *            user directory
	 * @return list of {@link ProxyObject}s representing the folders and files
	 *         reachable by any step of the given path
	 */
	public List<ProxyObject> getRequestedModelFile(String relative_remote_model_path){
		File file = this.modelIndexer.getFile(relative_remote_model_path);
		return ProxyUtil.convertFileTree(file, user_folder, this.modelIndexer.getFilteredFiles().values());
	}
	
	/**
	 * Returns the requested model elements as hierarchically structured proxy
	 * objects
	 * 
	 * @param relative_remote_model_path
	 *            path for the requested remote model file relative to the current
	 *            user directory
	 * @param relative_local_model_path
	 *            path of the local model file starting with the project name
	 * @return list of {@link ProxyObject}s representing the requested model
	 *         elements
	 * @throws GetRequestedModelElementsException 
	 */
	public List<ProxyObject> getRequestedModelElements(String relative_remote_model_path, String relative_local_model_path) throws GetRequestedModelElementsException {
		
		String absolute_origin_path = user_folder.getAbsolutePath() + File.separator + relative_remote_model_path;
		String sidiff_inf_path = new Path(absolute_origin_path).removeLastSegments(1).toOSString() + File.separator + SIDIFF_INF;
		String absolute_copy_path = sidiff_inf_path + File.separator + relative_local_model_path;
		
		URI completeModelURI = EMFStorage.pathToUri(absolute_copy_path);
		
		ResourceSet completeModelResourceSet = new ResourceSetImpl();
		UUIDResource completeModel;
		try {
			completeModel = new UUIDResource(completeModelURI, completeModelResourceSet);
		} catch (IOException e) {
			throw new GetRequestedModelElementsException(e);
		}
		
		URI slicedModelURI = completeModelURI.trimSegments(1).appendSegment("sliced_" + completeModelURI.lastSegment());
		
		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel;
		try {
			slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		} catch (IOException e) {
			throw new GetRequestedModelElementsException(e);
		}
		
		List<ProxyObject> proxyObjects = ProxyUtil.convertEMFResource(slicedModel, completeModel); 
		
		return proxyObjects;
	}
	
	/**
	 * Adds the repository to the remote application and checks out the files given
	 * by the path
	 * 
	 * @param url
	 *            the URL of the repository to be added to the remote application
	 * @param port
	 *            the port of the repository to be added to the remote application
	 * @param path
	 *            a relative path of the repository content to be copied to the
	 *            remote application
	 * @param user
	 *            the username for accessing the repository
	 * @param password
	 *            the password for accessing the repository
	 * @return a {@link CheckoutOperationResult}
	 * @throws AddRepositoryException
	 */
	public CheckoutOperationResult checkoutRepositoryContent(String url, int port, String path, String user, char[] password) throws AddRepositoryException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter =
				IRepositoryAdapter.MANAGER.getExtension("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter")
					.orElseThrow(() -> new SiDiffRuntimeException("repository adapter not found"));

		String target = this.user_folder.getPath();
		CheckoutOperationResult checkoutOperatoinResult = null;
		try {
			checkoutOperatoinResult = repositoryAdapter.checkout(url, port, path, user, password, target);
			modelIndexer.index();
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new AddRepositoryException(e);
		}
		
		return checkoutOperatoinResult;
	}
	
	/**
	 * Returns a slicing edit script for updating the local submodel
	 * 
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user
	 *            directory
	 * @param relative_local_model_path
	 *            path of the local model file starting with the project name
	 * @param elementIds
	 *            the requested model element IDs
	 * @return a slicing edit script for updating the local submodel
	 * @throws UpdateSubModelException
	 * 
	 */
	public File updateSubModel(String relative_remote_model_path, String relative_local_model_path, Set<String> elementIds, RemotePreferences preferences) throws UpdateSubModelException  {
		
		String absolute_origin_path = user_folder.getAbsolutePath() + File.separator + relative_remote_model_path;
		String sidiff_inf_path = new Path(absolute_origin_path).removeLastSegments(1).toOSString() + File.separator + SIDIFF_INF;
		String absolute_copy_path = sidiff_inf_path + File.separator + relative_local_model_path;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel;
		try {
			completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		} catch (IOException e) {
			throw new UpdateSubModelException(e);
		}
		
		URI emptydModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));

		ResourceSet emptyModelResourceSet = new ResourceSetImpl();
		UUIDResource emptyModel;
		try {
			emptyModel = new UUIDResource(emptydModelURI, emptyModelResourceSet);
		} catch (IOException e) {
			throw new UpdateSubModelException(e);
		}
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel;
		try {
			slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		} catch (IOException e) {
			throw new UpdateSubModelException(e);
		}
		
		File slicingEditScriptFile = null;
		try {
			slicingEditScriptFile = this.extractionEngine.update(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel, preferences);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | IOException | EmptySlicingCriteriaException | EmtpyModelSliceException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new UpdateSubModelException(e);
		}
		
		return slicingEditScriptFile;
	}	
}
