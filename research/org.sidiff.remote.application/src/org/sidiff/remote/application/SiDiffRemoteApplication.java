package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.remote.application.adapters.BrowseRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.CheckoutRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.application.extraction.ExtractionEngine;
import org.sidiff.remote.application.util.ExtensionUtil;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.exceptions.AddRepositoryException;
import org.sidiff.remote.common.exceptions.CheckoutSubModelException;
import org.sidiff.remote.common.exceptions.ListRepositoryContentException;
import org.sidiff.remote.common.exceptions.UpdateSubModelException;
import org.sidiff.remote.common.settings.ExtractionProperties;
import org.sidiff.remote.common.settings.GeneralProperties;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.ValidationProperties;
import org.sidiff.remote.common.util.ProxyUtil;
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
	public BrowseRepositoryContentOperationResult browseRepositoryContent(String url, int port, String path, String user, char[] password) throws ListRepositoryContentException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		BrowseRepositoryContentOperationResult browseRepositoryContentOperationResult = null;
		try {
			browseRepositoryContentOperationResult = repositoryAdapter.list(url, port, path, user, password);
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new ListRepositoryContentException(e);
		}
		return browseRepositoryContentOperationResult;
	}
	
	/**
	 * Browses the remote application content. If the requested file is a directory,
	 * its content is returned. If the requested file is a model file and no
	 * elementID is given, the root elements of the model are returned. If an
	 * elementID is given, the elements contained by the identified element are
	 * returned.
	 * 
	 * @param session_path
	 *            a session based path of a requested file, i.e. a path starting
	 *            with the sessionID
	 * @param elementID
	 *            the ID of a requested model element (only used if the requested
	 *            file is a model file)
	 * @return the content of the remote application as {@link List} of
	 *         {@link ProxyObject}s
	 */
	public List<ProxyObject> browseRemoteApplicationContent(String session_path, String elementID) {

		File file = this.modelIndexer.getFile(session_path);
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		if (file.isDirectory()) {
			for (File child : this.modelIndexer.getChildren(file)) {
				ProxyObject proxyObject = ProxyUtil.convertFile(child, this.session_id);
				proxyObjects.add(proxyObject);
			}
		} else {
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
			if(elementID == null) {
				for(EObject eObject : last_selected_model.getContents()) {
					ProxyObject proxyObject = ProxyUtil.convertEObject(eObject);
					proxyObjects.add(proxyObject);
				}
			}else {
				
				EObject eObject = last_selected_model.getIDToEObjectMap().get(elementID);
					
				for (EObject eObj : eObject.eContents()) {
					ProxyObject proxyObject = ProxyUtil.convertEObject(eObj);
					proxyObjects.add(proxyObject);
				}
				
			}
		}

		return proxyObjects;
	}	
	

	/**
	 * 
	 * Extracts a submodel containing at least all elements identified by the elementIDs
	 * 
	 * @param session_path
	 *            a session based path of a requested file, i.e. a path starting
	 *            with the sessionID
	 * @param local_model_path
	 *            workspace location path of the model file
	 * @param elementIds
	 *            the IDs of the model elements to be checked out.
	 * @param preferences 
	 * @return a {@link File} containing a submodel with all requested model
	 *         elements
	 * @throws CheckoutSubModelException
	 */
	public File checkoutModel(String session_path, String local_path, Set<String> elementIds, RemotePreferences preferences) throws CheckoutSubModelException {
		
		String absolute_origin_path = user_folder + File.separator + session_path;
		String absolute_copy_path = sidiff_folder + File.separator + local_path;
//		try {
//			FileOperations.copyFile(absolute_origin_path, absolute_copy_path);
//		} catch (IOException e) {
//			LogUtil.log(LogEvent.ERROR, e.getMessage());
//			throw new CheckoutSubModelException(e);
//		}
		
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource sessionModel = new UUIDResource(EMFStorage.pathToUri(absolute_origin_path), resourceSet);
		
		UUIDResource completeModel = UUIDResourceUtil.copyResource(sessionModel, EMFStorage.pathToUri(absolute_copy_path));
		
		// save complete model as uuid resource
		try {
			completeModel.save(completeModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		URI emptyModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "empty_" + completeModel.getURI().lastSegment()));
	
//		ResourceSet emptyModelResourceSet = new ResourceSetImpl();
//
//		UUIDResource emptyModel = UUIDResource.createUUIDResource(emptyModelURI, emptyModelResourceSet);
//		Map<EObject, EObject> copies_empty = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
//		emptyModel.getContents().addAll(copies_empty.values());
//		for (EObject origin : copies_empty.keySet()) {
//			String id = EMFUtil.getXmiId(origin);
//			EMFUtil.setXmiId(copies_empty.get(origin), id);
//			System.out.println(EMFUtil.getXmiId(origin) + " : " + EMFUtil.getXmiId(copies_empty.get(origin)));
//		}
		
		UUIDResource emptyModel = UUIDResourceUtil.copyMinimalResource(completeModel, emptyModelURI);
		try {
			emptyModel.save(emptyModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}

	
		URI slicedModelURI = EMFStorage.pathToUri(EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "sliced_" + completeModel.getURI().lastSegment()));

//		ResourceSet slicedModelResourceSet = new ResourceSetImpl();
//
//		UUIDResource slicedModel = UUIDResource.createUUIDResource(slicedModelURI, slicedModelResourceSet);
//		Map<EObject, EObject> copies_sliced = EMFUtil.copySubModel(new HashSet<EObject>(completeModel.getContents()));
//		slicedModel.getContents().addAll(copies_sliced.values());
//		for (EObject origin : copies_sliced.keySet()) {
//			String id = EMFUtil.getXmiId(origin);
//			EMFUtil.setXmiId(copies_sliced.get(origin), id);
//		}
		
		UUIDResource slicedModel = UUIDResourceUtil.copyResource(emptyModel, slicedModelURI);
		try {
			slicedModel.save(slicedModel.getDefaultSaveOptions());
		} catch (IOException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}

		File subModelFile = null;
		try {
			subModelFile = this.extractionEngine.extract(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel, preferences);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | IOException
				| CoreException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new CheckoutSubModelException(e);
		}
		
		return subModelFile;
	}
	
	/**
	 * Returns the file tree as {@link ProxyObject}s for the file given by the session path
	 * 
	 * @param session_path
	 *            session based path for the requested file
	 * @return the file tree as {@link ProxyObject}s for the file given by the session path
	 */
	public List<ProxyObject> getRequestedModelFile(String session_path){
		File file = this.modelIndexer.getFile(session_path);
		return ProxyUtil.convertFileTree(file, session_id, this.modelIndexer.getFilteredFiles().values());
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
	 * @return a {@link CheckoutRepositoryContentOperationResult}
	 * @throws AddRepositoryException 
	 */
	public CheckoutRepositoryContentOperationResult checkoutRepositoryContent(String url, int port, String path, String user, char[] password) throws AddRepositoryException {
		//TODO determine right repository adapter
		IRepositoryAdapter repositoryAdapter = ExtensionUtil.getRepositoryAdapter("org.sidiff.remote.application.adapter.svn.SVNRepositoryAdapter");
		String target = this.session_folder.getPath();
		CheckoutRepositoryContentOperationResult checkoutRepositoryContentOperationResult = null;
		try {
			checkoutRepositoryContentOperationResult = repositoryAdapter.checkout(url, port, path, user, password, target);
			modelIndexer.index();
		} catch (RepositoryAdapterException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new AddRepositoryException(e);
		}
		
		return checkoutRepositoryContentOperationResult;
	}
	
	/**
	 * Returns a slicing edit script for updating the local submodel
	 * @param local_path
	 * 				workspace location path of the model file
	 * @param elementIds
	 * 				the requested model element IDs
	 * @return a slicing edit script for updating the local submodel
	 * @throws UpdateSubModelException 

	 */
	public File updateSubModel(String local_path, Set<String> elementIds, RemotePreferences preferences) throws UpdateSubModelException  {
		String absolute_copy_path = sidiff_folder + File.separator + local_path;
		
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
			slicingEditScriptFile = this.extractionEngine.update(new HashSet<String>(elementIds), completeModel, emptyModel, slicedModel, preferences);
		} catch (UncoveredChangesException | InvalidModelException | NoCorrespondencesException
				| NotInitializedException | ExtendedSlicingCriteriaIntersectionException | CoreException e) {
			LogUtil.log(LogEvent.ERROR, e.getMessage());
			throw new UpdateSubModelException(e);
		}
		
		return slicingEditScriptFile;
	}
	
	public RemotePreferences getRemotePreferences() {
		
		Map<String,String> scope_items = new HashMap<String, String>();
		scope_items.put(Scope.RESOURCE.name(), "Resource");
		scope_items.put(Scope.RESOURCE_SET.name(), "Resource Set");
		
		SingleSelectionRemoteApplicationProperty<String> scope =
				new SingleSelectionRemoteApplicationProperty<String>(GeneralProperties.SCOPE, scope_items, Scope.RESOURCE.name());
		
		GeneralProperties generalProperties = new GeneralProperties(scope);
		
		Map<String,Boolean> boolean_items = new HashMap<String, Boolean>();
		boolean_items.put(Boolean.toString(true), true);
		boolean_items.put(Boolean.toString(false), false);
		
		SingleSelectionRemoteApplicationProperty<Boolean> mergeImports =
				new SingleSelectionRemoteApplicationProperty<Boolean>(ExtractionProperties.MERGE_IMPORTS, boolean_items, true);
		SingleSelectionRemoteApplicationProperty<Boolean> unmergeImports =
				new SingleSelectionRemoteApplicationProperty<Boolean>(ExtractionProperties.UNMERGE_IMPORTS, boolean_items, true);

		Map<String, List<ITechnicalDifferenceBuilder>> builders = new HashMap<>();
		for(ITechnicalDifferenceBuilder technicalDifferenceBuilder : PipelineUtils.getAllAvailableTechnicalDifferenceBuilders()) {
			for(String documentType : technicalDifferenceBuilder.getDocumentTypes()){
				if(!builders.containsKey(documentType)) {
					builders.put(documentType, new ArrayList<ITechnicalDifferenceBuilder>());
				}
				builders.get(documentType).add(technicalDifferenceBuilder);
			}
		}
		
		List<MultiSelectionRemoteApplicationProperty<String>> technicalDifferenceBuilderProperties = new ArrayList<>();
		for(String documentType : builders.keySet()) {
			Map<String, String> builder_items = new HashMap<String, String>();
			List<String> builder_values  = new ArrayList<String>();
			for(ITechnicalDifferenceBuilder builder : builders.get(documentType)) {
				builder_items.put(builder.getKey(), builder.getName());
			}
			builder_values.add(builder_items.keySet().iterator().next());
			MultiSelectionRemoteApplicationProperty<String> multiSelectionRemoteApplicationProperty =
					new MultiSelectionRemoteApplicationProperty<String>(
							ExtractionProperties.TECHNICAL_DIFFERENCE_BUILDER, builder_items, builder_values);
			multiSelectionRemoteApplicationProperty.setDocumentType(documentType);
			technicalDifferenceBuilderProperties.add(multiSelectionRemoteApplicationProperty);
		}
		
		Map<String, List<IRecognitionRuleSorter>> sorters = new HashMap<String, List<IRecognitionRuleSorter>>();
		for(IRecognitionRuleSorter recognitionRuleSorter : PipelineUtils.getAllAvailableRecognitionRuleSorters()) {
			
				if(!sorters.containsKey(recognitionRuleSorter.getDocumentType())) {
					sorters.put(recognitionRuleSorter.getDocumentType(), new ArrayList<IRecognitionRuleSorter>());
				}
				sorters.get(recognitionRuleSorter.getDocumentType()).add(recognitionRuleSorter);
		}
		
		List<SingleSelectionRemoteApplicationProperty<String>> recognitionRuleSorterProperties = new ArrayList<>();
		for(String documentType : sorters.keySet()) {
			Map<String, String> sorter_items = new HashMap<String, String>();
			for(IRecognitionRuleSorter sorter : sorters.get(documentType)) {
				sorter_items.put(sorter.getKey(), sorter.getName());
			}
			SingleSelectionRemoteApplicationProperty<String> singleSelectionRemoteApplicationProperty =
					new SingleSelectionRemoteApplicationProperty<String>(ExtractionProperties.RECOGNITION_RULE_SORTER, sorter_items,
							sorter_items.keySet().iterator().next());
			singleSelectionRemoteApplicationProperty.setDocumentType(documentType);
			recognitionRuleSorterProperties.add(singleSelectionRemoteApplicationProperty);
		}
		
		Map<String, List<ILiftingRuleBase>> rules = new HashMap<String, List<ILiftingRuleBase>>();
		for(ILiftingRuleBase rulesBase : PipelineUtils.getAllAvailableRulebases()) {
			for(String documentType : rulesBase.getDocumentTypes()) {
				if(!rules.containsKey(documentType)) {
					rules.put(documentType, new ArrayList<ILiftingRuleBase>());
				}
				rules.get(documentType).add(rulesBase);
			}
		}
		
		List<MultiSelectionRemoteApplicationProperty<String>> ruleBaseProperties = new ArrayList<>();
		for(String documentType : rules.keySet()) {
			Map<String, String> ruleBase_items = new HashMap<String, String>();
			for(ILiftingRuleBase ruleBase : rules.get(documentType)) {
				ruleBase_items.put(ruleBase.getName(), ruleBase.getName());
			}
			List<String> ruleBase_values = new ArrayList<String>(ruleBase_items.keySet());
			MultiSelectionRemoteApplicationProperty<String> multiSelectionRemoteApplicationProperty =
					new MultiSelectionRemoteApplicationProperty<String>(ExtractionProperties.RULE_BASE, ruleBase_items, ruleBase_values);
			multiSelectionRemoteApplicationProperty.setDocumentType(documentType);
			ruleBaseProperties.add(multiSelectionRemoteApplicationProperty);
		}

		ExtractionProperties extractionProperties = new ExtractionProperties(mergeImports, unmergeImports,
				technicalDifferenceBuilderProperties, recognitionRuleSorterProperties, ruleBaseProperties);
		
		SingleSelectionRemoteApplicationProperty<Boolean> validateModels =
				new SingleSelectionRemoteApplicationProperty<Boolean>(ValidationProperties.VALIDATE_MODELS, boolean_items, true);
		
		ValidationProperties validationProperties = new ValidationProperties(validateModels);
		
		RemotePreferences remotePreferences = new RemotePreferences(generalProperties, extractionProperties, validationProperties);
		
		return remotePreferences;
	}
}
