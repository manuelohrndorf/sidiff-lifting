package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
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
import org.sidiff.remote.application.extraction.ExtractionEngine;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.exceptions.ModelNotVersionedException;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.util.TreeModelUtil;
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
	
	private Session session;
	
	private File user_folder;
	
	private File session_folder;
	
	
	private ModelIndexer modelIndexer;
	
	private ExtractionEngine extractionEngine;
	
	public SiDiffRemoteApplication(IWorkspace workspace, Session session) throws CoreException {
		this.workspace = workspace;
		this.session = session;
		String ws_path = this.workspace.getRoot().getLocation().toOSString();
		String	user_path= ws_path + File.separator + session.getUser();
		String session_path = user_path + File.separator + session.getSessionID();
		
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
	}
	
	/**
	 * 
	 * @param sessionID
	 * @return
	 */
	public TreeModel browseModel(String sessionID) {
		String absolute_path = user_folder + File.separator + sessionID;
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource uuidResource = new UUIDResource(EMFStorage.pathToUri(absolute_path), resourceSet);
		TreeModel treeModel = TreeModelUtil.convertEMFResource(uuidResource);
		return treeModel;
	}
	
	/**
	 * 
	 * @param remote_model_path
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
	 */
	public File checkoutModel(String remote_model_path, String local_model_path, Set<String> elementIds) throws IOException, UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException {
		
		String absolute_origin_path = user_folder + File.separator + remote_model_path;
		String absolute_copy_path = session_folder + File.separator + local_model_path;
		FileOperations.copyFile(absolute_origin_path, absolute_copy_path);
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		URI emptyModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));
	
		ResourceSet emptyModelResourceSet = new ResourceSetImpl();
//		UUIDResource emptyModel = new UUIDResource(emptyModelURI, emptyModelResourceSet);
		
		
//		if(emptyModelResourceSet.getURIConverter().exists(emptyModelURI, emptyModel.getDefaultLoadOptions())) {
			UUIDResource emptyModel = UUIDResource.createUUIDResource(emptyModelURI, emptyModelResourceSet);
			Map<EObject, EObject> copies_empty = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
			emptyModel.getContents().addAll(copies_empty.values());
			for(EObject origin : copies_empty.keySet()){
				String id = EMFUtil.getXmiId(origin);
				EMFUtil.setXmiId(copies_empty.get(origin), id);
				System.out.println(EMFUtil.getXmiId(origin) + " : " + EMFUtil.getXmiId(copies_empty.get(origin)));
			}
			emptyModel.save(emptyModel.getDefaultSaveOptions());
//		}
	
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
//		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
//		if(slicedModelResourceSet.getURIConverter().exists(slicedModelURI, slicedModel.getDefaultLoadOptions())) {
			UUIDResource slicedModel = UUIDResource.createUUIDResource(slicedModelURI, slicedModelResourceSet);
			Map<EObject, EObject> copies_sliced = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
			slicedModel.getContents().addAll(copies_sliced.values());
			for(EObject origin : copies_sliced.keySet()){
				String id = EMFUtil.getXmiId(origin);
				EMFUtil.setXmiId(copies_sliced.get(origin), id);
			}
			slicedModel.save(slicedModel.getDefaultSaveOptions());
//		}
		
		return this.extractionEngine.extract(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel);
	}
	
	/**
	 * 
	 * @param local_model_path
	 * 				project relative local model path
	 * @return
	 */
	public TreeModel getRequestedModelElements(String local_model_path) {
		String absolute_copy_path = session_folder + File.separator + local_model_path;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource completeModel = new UUIDResource(EMFStorage.pathToUri(absolute_copy_path), resourceSet);
		
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
		UUIDResource slicedModel = new UUIDResource(slicedModelURI, slicedModelResourceSet);
		
		TreeModel treeModel = TreeModelUtil.convertEMFResource(completeModel);
		
		for(String id: slicedModel.getIDToEObjectMap().keySet()) {
			treeModel.getTreeNode(id).setSelected(true);
		}
		
		return treeModel;
	}
	
	public void addRepository(String url, int port, String user, char[] passowrd) {
		//TODO connect to repository and clone/checkout branches
		
	}
	
	/**
	 * 
	 * @param localModelPath
	 * 				project relative local model path
	 * @return
	 * @throws ModelNotVersionedException
	 */
	public TreeModel browseModelFiles(String localModelPath) throws ModelNotVersionedException {
		this.modelIndexer.index();
		List<File> files = this.modelIndexer.getModel_files();
		TreeModel treeModel = TreeModelUtil.convertFileList(files, session.getSessionID());
		if(localModelPath != null) {
			String remoteModelPath = session.getRemoteModelPath(localModelPath);
			treeModel.getTreeNode(remoteModelPath).setSelected(true);
		}
		return treeModel;
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
	 */
	public File updateSubModel(String localPath, Set<String> elementIds) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException {
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
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
}
