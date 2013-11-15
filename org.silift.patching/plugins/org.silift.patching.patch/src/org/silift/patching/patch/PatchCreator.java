package org.silift.patching.patch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLWriter;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.util.emf.EMFStorage;
import org.sidiff.patching.util.PatchUtil;
import org.silift.common.file.util.FileOperations;
import org.silift.common.file.util.ZipUtil;


public class PatchCreator {
	
	private Resource resourceA;
	private Resource resourceB;
	private AsymmetricDifference asymmetricDifference;
	private SymmetricDifference symmetricDifference;
	private String savePath;
	
	private String separator;
	
	private String resourceA_name;
	private String resourceB_name;
	
	private String relativeResASavePath;
	private String relativeResBSavePath;
	
	private String symmetricDiff_name;
	private String asymmetricDiff_name;
	
	private String relativeSymDiffPath;
	private String relativeAsymDiffPath;
	
	
	private ArrayList<HashMap<String,String>> editRules;
	private ArrayList<HashMap<String,String>> differences;
	private ArrayList<HashMap<String,String>> settings;
	
	
	public PatchCreator(Resource resourceA, Resource resourceB){
		this.resourceA = resourceA;
		this.resourceB = resourceB;
		separator = System.getProperty("file.separator");
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


	public ArrayList<HashMap<String, String>> getSettings() {
		return settings;
	}


	public void setSettings(ArrayList<HashMap<String, String>> settings) {
		this.settings = settings;
	}


	public void serializePatch(IPath path){
		resourceA_name = resourceA.getURI().lastSegment();
		resourceB_name = resourceB.getURI().lastSegment();
		savePath = path.toOSString()+separator+"PATCH(origin_"+resourceA_name+"_to_"+"modified_"+resourceB_name+")";
		
		String resASavePath = savePath+separator+"modelA"+separator+resourceA_name;
		String resBSavePath = savePath+separator+"modelB"+separator+resourceB_name;

		LogUtil.log(LogEvent.NOTICE, "serialize "+ resourceA_name + " to " + resASavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resASavePath), resourceA.getContents().get(0), true);
		
		LogUtil.log(LogEvent.NOTICE, "serialize "+ resourceB_name + " to " + resBSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resBSavePath), resourceB.getContents().get(0), true);

		relativeResASavePath = EMFStorage.pathToRelativeUri(savePath, resASavePath).toString();
		relativeResBSavePath = EMFStorage.pathToRelativeUri(savePath, resBSavePath).toString();
		
		symmetricDifference.setUriModelA(relativeResASavePath);
		symmetricDifference.setUriModelB(relativeResBSavePath);
		
		editRules = new ArrayList<HashMap<String,String>>();
		for(RuleBase rb : asymmetricDifference.getRuleBases()){
			for(RuleBaseItem rbi : rb.getItems()){
				Module module = rbi.getEditRule().getExecuteModule();
				String erSavePath = savePath + separator + "EditRules" + separator + module.getName() + ".henshin";
				
				LogUtil.log(LogEvent.NOTICE, "serialize "+ rbi.getEditRule().getExecuteModule().getName() + " to " + erSavePath);
				EMFStorage.eSaveAs(EMFStorage.pathToUri(erSavePath), module, true);
				Module newMod = (Module)EMFStorage.eLoad(EMFStorage.pathToUri(erSavePath));
				rbi.getEditRule().setExecuteMainUnit(newMod.getUnit("mainUnit"));
				
				
				// MANIFEST
				String relSavePath = EMFStorage.pathToRelativeUri(savePath, erSavePath).toString();
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put("name", module.getName());
				attributes.put("href", relSavePath);
				editRules.add(attributes);
			}
		}
		
		symmetricDiff_name = resourceA_name + "_x_" + resourceB_name + "." + LiftingFacade.SYMMETRIC_DIFF_EXT;
		String symmetricDiffSavePath = savePath + separator + symmetricDiff_name ;
		LogUtil.log(LogEvent.NOTICE, "serialize symmetric difference "+ " to " + symmetricDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(symmetricDiffSavePath), symmetricDifference, true);
		relativeSymDiffPath = EMFStorage.pathToRelativeUri(savePath, symmetricDiffSavePath).toString();
		
		asymmetricDiff_name = resourceA_name + "_x_" + resourceB_name + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;
		String asymmetricDiffSavePath = savePath + separator +asymmetricDiff_name;
		LogUtil.log(LogEvent.NOTICE, "serialize asymmetric difference "+ " to " + asymmetricDiffSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(asymmetricDiffSavePath), asymmetricDifference, true);
		relativeAsymDiffPath = EMFStorage.pathToRelativeUri(savePath, asymmetricDiffSavePath).toString();
		
		// MANIFEST
		differences = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> symAtritbutes = new HashMap<String, String>();
		symAtritbutes.put("name", symmetricDiff_name);
		symAtritbutes.put("href", relativeSymDiffPath);
		differences.add(symAtritbutes);
		HashMap<String, String> asymAtritbutes = new HashMap<String, String>();
		asymAtritbutes.put("name", asymmetricDiff_name);
		asymAtritbutes.put("href", relativeAsymDiffPath);
		differences.add(asymAtritbutes);
		
		try {
			createManifest(savePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// zip all necessary files
		ZipUtil.zip(savePath, savePath, PatchUtil.PATCH_EXTENSION);
		FileOperations.removeFolder(savePath);

		
	}
	
	private void createManifest(String path) throws FileNotFoundException {
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(path+separator+"MANIFEST.xml")));
		writer.initDocument("manifest");
		createManifestElement(writer, "modelA", resourceA_name, relativeResASavePath);
		createManifestElement(writer, "modelB", resourceB_name, relativeResBSavePath);
		createManifestElement(writer, "editrules", "editrule", editRules);;
		createManifestElement(writer, "differences", "difference", differences);
		createManifestElement(writer, "settings", "setting", settings);
		writer.finishDocument();
	}
	
	private void createManifestElement(XMLWriter xmlWriter, String name, String attName, String attUri){
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("name", attName);
		attributes.put("href", attUri);
		xmlWriter.generateEmptyTag(name, attributes);
	}
	
	private void createManifestElement(XMLWriter xmlWriter, String name, String innerName, ArrayList<HashMap<String, String>> innerElements){
		xmlWriter.generateStartTag(name, null);
		for(HashMap<String, String> attributes : innerElements){
			xmlWriter.generateEmptyTag(innerName, attributes);	
		}
		
		xmlWriter.generateEndTag(name);
	}

}
