package org.silift.patching.patch;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.facade.LiftingSettings;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.util.emf.EMFResourceUtil;
import org.sidiff.difference.util.emf.EMFStorage;
import org.silift.common.exceptions.FileAlreadyExistsException;
import org.silift.common.exceptions.FileNotCreatedException;
import org.silift.common.file.util.FileOperations;


public class PatchCreator {
	
	private Resource resourceA;
	private Resource resourceB;
	private AsymmetricDifference asymmetricDifference;
	private SymmetricDifference symmetricDifference;
	private String savePath;
	
	public PatchCreator(Resource resourceA, Resource resourceB){
		this.resourceA = resourceA;
		this.resourceB = resourceB;
	}
	
	
	public Resource getResourceA() {
		return resourceA;
	}

	public void setResourceA(Resource resourceA) {
		this.resourceA = resourceA;
	}

	
	public Resource getResourceB() {
		return resourceB;
	}

	public void setResourceB(Resource resourceB) {
		this.resourceB = resourceB;
	}

	
	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}

	public void setAsymmetricDifference(AsymmetricDifference asymmetricDifference) {
		this.asymmetricDifference = asymmetricDifference;
		this.asymmetricDifference.initRuleBases();
		for(RuleBase rb : this.asymmetricDifference.getRuleBases()){
			System.out.println(rb);
			for(RuleBaseItem i: rb.getItems()){
				System.out.println(i.getEditRule().toString());
			}
		}
	}

	
	public SymmetricDifference getSymmetricDifference() {
		return symmetricDifference;
	}


	public void setSymmetricDifference(SymmetricDifference symmetricDifference) {
		this.symmetricDifference = symmetricDifference;
	}


	public String getSavePath() {
		return savePath;
	}


	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}


	public void serializePatch(IPath path){
		String separator = System.getProperty("file.separator");
		String resourceA_name = resourceA.getURI().lastSegment();
		String resourceB_name = resourceB.getURI().lastSegment();
		savePath = path.toOSString()+separator+"PATCH(origin_"+resourceA_name+"_to_"+"modified_"+resourceB_name+")";
		
		
//			FileOperations.createFolder(savePath, false);
//			FileOperations.createFolder(savePath+separator+"modelA", false);
//			FileOperations.createFolder(savePath+separator+"modelB", false);
			
		EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath+separator+"modelA"+separator+resourceA_name), resourceA.getContents().get(0));
		EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath+separator+"modelB"+separator+resourceB_name), resourceB.getContents().get(0));
	
		symmetricDifference.setUriModelA(EMFStorage.pathToUri(savePath+separator+"modelA"+separator+resourceA_name).toString());
		symmetricDifference.setUriModelB(EMFStorage.pathToUri(savePath+separator+"modelB"+separator+resourceB_name).toString());
		EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + resourceA_name + "_x_" + resourceB_name + "." + LiftingFacade.SYMMETRIC_DIFF_EXT), symmetricDifference);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(savePath + separator + resourceA_name + "_x_" + resourceB_name + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT), asymmetricDifference);
			
//			String split = separator.equals("\\")? Pattern.quote("\\"): separator;
//			String[] folderA = fileA_name.split(split);
//			String[] folderB = fileB_name.split(split);
//			String pathA = diffSavePath + separator;
//			String pathB = diffSavePath+separator;
//			
//			for(int i = 0 ; i < folderA.length-1 ; i++){
//				pathA += separator + folderA[i];
//				FileOperations.createFolder(pathA, false);
//			}
//			for(int i = 0 ; i < folderB.length-1 ; i++){
//				pathB += separator + folderB[i];
//				FileOperations.createFolder(pathB, false);
//			}
//			FileOperations.copyFile(fileA.getLocation().toOSString(), pathA + separator + fileA.getName());
//			FileOperations.copyFile(fileB.getLocation().toOSString(), pathB + separator + fileB.getName());
//			FileOperations.createInfoFile(diffSavePath, fileA.getProject().getName());
		
	}

}
