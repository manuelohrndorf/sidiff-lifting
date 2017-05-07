package org.sidiff.repair.evaluation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.api.TechnicalDifferenceFacade;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.repair.evaluation.settings.EvaluationSettings;
import org.sidiff.repair.evaluation.validation.EMFValidator;
import org.sidiff.repair.evaluation.validation.IValidator;
import org.sidiff.repair.historymodel.History;
import org.sidiff.repair.historymodel.HistoryModelFactory;
import org.sidiff.repair.historymodel.ModelStatus;
import org.sidiff.repair.historymodel.ValidationError;
import org.sidiff.repair.historymodel.Version;

public class HistoryModelGenerator {
	
	private static final String VERSIONS_FOLDER = "versions";
	private static final String DIFF_FOLDER = "differences";
	
	private static IProject project = null;
	
	public static void generateHistoryProject(String folderpath, EvaluationSettings settings) throws CoreException{
		// scan for model files within that folder
		File modelFolder = new File(folderpath);
		List<File> files = searchModelFiles(modelFolder, settings);

		
		if(!files.isEmpty()){
			
			// get the root of the workspace
			//
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			
			// get the project the history shall be stored in
			//
			project = root.getProject("org.sidiff.repair.history."+modelFolder.getName().toLowerCase());
			
			project.create(null);
			project.open(null);
			
			// create the respective folders
			IFolder versionF = project.getFolder(VERSIONS_FOLDER);
			versionF.create(false, true, null);
			IFolder diffF = project.getFolder(DIFF_FOLDER);
			diffF.create(false, true, null);
			
			// copy the model files
			List<Resource> resources = copyModels(files);
			
			History history = generateHistory(resources, settings);
			EMFStorage.eSaveAs(EMFStorage.pathToUri(project.getLocation().toOSString() + File.separator + history.getName() + ".history"), history);
		}
	}
	
	private static List<File> searchModelFiles(File root, EvaluationSettings settings){
		List<File> files = new ArrayList<File>();
		
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(!pathname.isDirectory()){
					for (String filter : settings.getFileFilters()) {
						if (pathname.getName().toLowerCase().endsWith(filter)) {
							return true;
						}
					}
				}
				return false;
			}
		};

		files.addAll(Arrays.asList(root.listFiles(filter)));
		Collections.sort(files);
		// scan for sub-directories recursively
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				files.addAll(searchModelFiles(file, settings));
			}
		}
		return files;
	}
	
	private static List<Resource> copyModels(List<File> files){
		List<Resource> resources = new LinkedList<Resource>();
		for(File modelFile : files){
			Resource model = EMFStorage.eLoad(EMFStorage.fileToFileUri(modelFile)).eResource();
			URI targetURI = EMFStorage.pathToUri(project.getLocation().toOSString() + File.separator + VERSIONS_FOLDER + File.separator + modelFile.getName().substring(0,3) + modelFile.getName().substring(modelFile.getName().lastIndexOf(".")));
			EMFStorage.eSaveAs(targetURI, model.getContents().get(0));
			resources.add(EMFStorage.eLoad(targetURI).eResource());
		}
		return resources;
	}
	
	private static History generateHistory(List<Resource> resources, EvaluationSettings settings) {		
		
		History history = HistoryModelFactory.eINSTANCE.createHistory();
		history.setName(settings.getHistory_name());
		for (Resource resource : resources) {
			Version version = generateVersion(resource);
			if(version != null){
				history.getVersions().add(version);
			}
		}	
		
		
		for(int i = 0 ; i < history.getVersions().size()-1; i++){
			Version versionA = history.getVersions().get(i);
			if(i == 0){
				generateUUIDs(versionA);
			}
			int j = i+1;
			Version versionB = history.getVersions().get(j);
			while(versionB.getStatus().equals(ModelStatus.DEFECT) && j < history.getVersions().size()-1){
				versionB = history.getVersions().get(++j);
			}
			try {
				if(versionA.getStatus().equals(ModelStatus.DEFECT) || versionB.getStatus().equals(ModelStatus.DEFECT)) continue;				
				SymmetricDifference diff = generateTechnicalDifference(versionA, versionB, settings);
				generateUUIDs(diff);
				history.getTechnicalDifferences().add(diff);
			} catch (InvalidModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoCorrespondencesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		generateIntroducedAndResolved(history);
		
		return history;
	}
	
	
	
	private static Version generateVersion(Resource resource){

		Resource model = resource;
		EcoreUtil.resolveAll(model);
		IValidator validator = new EMFValidator();
		Collection<ValidationError> validationErrors = validator.validate(model);
		
		ModelStatus modelStatus = validationErrors.isEmpty()? ModelStatus.VALID : ModelStatus.INVALID;
		// Do not handle defect models
		for (ValidationError validationError : validationErrors) {
			if (validationError.getMessage().contains("contains an unresolved proxy")
					|| validationError.getMessage().contains("contains a dangling reference")) {
				modelStatus = ModelStatus.DEFECT;
				return null;
			}
		}
		
		Version version = HistoryModelFactory.eINSTANCE.createVersion();
		version.setName(model.getURI().lastSegment());
		version.setModelURI(URI.createPlatformResourceURI(model.getURI().toPlatformString(false), true).toString());
		version.getValidationErrors().addAll(validationErrors);
		version.setStatus(modelStatus);
		
		return version;
	}
	
	private static SymmetricDifference generateTechnicalDifference(Version versionA, Version versionB, EvaluationSettings settings) throws InvalidModelException, NoCorrespondencesException{
		Resource resourceA = versionA.getModel();
		Resource resourceB = versionB.getModel();
		SymmetricDifference diff= TechnicalDifferenceFacade.deriveTechnicalDifference(resourceA, resourceB, settings.getDifferenceSettings());
		
		TechnicalDifferenceFacade.serializeTechnicalDifference(diff, project.getLocation().toOSString() + File.separator + DIFF_FOLDER, versionA.getName() + "_x_" + versionB.getName());
		return diff;
	}
	
	private static void generateUUIDs(Version version){
		for (Iterator<EObject> iterator = version.getModel().getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			String uuid = EMFUtil.getXmiId(eObject);
			if(uuid == null || uuid.trim().isEmpty()){
				uuid = EcoreUtil.generateUUID();
				EMFUtil.setXmiId(eObject, uuid);
			}
		}
		try {
			version.getModel().save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void generateUUIDs(SymmetricDifference diff){
		for(Correspondence correspondence : diff.getMatching().getCorrespondences()){
			String uuid = EMFUtil.getXmiId(correspondence.getMatchedA());
			assert uuid!=null : "UUID for element" + correspondence.getMatchedA() + "not set!";
			EMFUtil.setXmiId(correspondence.getMatchedB(), uuid);
		}
		for(EObject eObject : diff.getMatching().getUnmatchedB()){
			String uuid = EcoreUtil.generateUUID();
			EMFUtil.setXmiId(eObject, uuid);
		}
		try {
			diff.getModelB().save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void generateIntroducedAndResolved(History history) {
		for(Version versionA : history.getVersions()){
			if(!history.getSuccessorRevisions(versionA).isEmpty()){
				Version versionB = history.getSuccessorRevisions(versionA).get(0);
				for(ValidationError errorB : versionB.getValidationErrors()){
					boolean hasCorresponding = false;
					for(ValidationError errorA : versionA.getValidationErrors()){
						if(errorA.equals(errorB)){
							errorB.setPrec(errorA);
							hasCorresponding = true;
							errorB.setIntroducedIn(errorA.getIntroducedIn());
							break;
						}
					}
					if(!hasCorresponding){
						errorB.setIntroducedIn(versionB);
					}
				}
			}
		}
		List<Version> reverseOrder = new ArrayList<Version>();
		reverseOrder.addAll(history.getVersions());
		Collections.reverse(reverseOrder);
		for(Version versionB : reverseOrder){
			if(!history.getPrecessorRevisions(versionB).isEmpty()){
				Version versionA = history.getPrecessorRevisions(versionB).get(0);
				for(ValidationError errorA : versionA.getValidationErrors()){
					boolean hasCorresponding = false;
					for(ValidationError errorB : versionB.getValidationErrors()){
						if(errorA.equals(errorB)){
							errorA.setSucc(errorB);
							hasCorresponding = true;
							errorA.setResolvedIn(errorB.getResolvedIn());
							break;
						}
					}
					if(!hasCorresponding){
						errorA.setResolvedIn(versionB);
					}
				}
			}
		}
	}
}
