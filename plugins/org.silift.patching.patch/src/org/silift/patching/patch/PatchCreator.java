package org.silift.patching.patch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLWriter;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.file.FileOperations;
import org.silift.common.util.file.ZipUtil;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;


public class PatchCreator {
	
	public static final String FOLDER_EDIT_RULES = "EditRules";
	public static final String FOLDER_MODEL_A = "modelA";
	public static final String FOLDER_MODEL_B = "modelB";
	
	private LiftingSettings settings;
	private Collection<SymbolicLinks> symbolicLinksSet;
	private Resource resourceA;
	private Resource resourceB;
	private ResourceSet resourceADiag;
	private ResourceSet resourceBDiag;
	private AsymmetricDifference asymmetricDifference;
	private SymmetricDifference symmetricDifference;
	
	
	private String separator;
	private String savePath;
	
	private String resourceA_name;
	private String resourceB_name;
	
	private String relativeResASavePath;
	private String relativeResBSavePath;
	
	private String symmetricDiff_name;
	private String asymmetricDiff_name;
	
	private String relativeSymDiffPath;
	private String relativeAsymDiffPath;
	
	
	private ArrayList<HashMap<String,String>> manifest_EditRules;
	private ArrayList<HashMap<String,String>> manifest_Differences;
	private ArrayList<HashMap<String,String>> manifest_Settings;
	
	
	public PatchCreator(Resource resourceA, Resource resourceB, LiftingSettings settings){
		this.settings = settings;
		this.resourceA = resourceA;
		this.resourceB = resourceB;
		this.resourceADiag = EMFModelAccessEx.deriveDiagramFile(resourceA);
		this.resourceBDiag = EMFModelAccessEx.deriveDiagramFile(resourceB);
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
	

	public String serializePatch(IPath path) throws FileNotFoundException{
		
		resourceA_name = resourceA.getURI().lastSegment();
		resourceB_name = resourceB.getURI().lastSegment();
		
		savePath = path.toOSString()+separator+"PATCH(origin_"+resourceA_name+"_to_"+"modified_"+resourceB_name+")";
		
		if(!settings.useSymbolicLinks()){
		String modelADir = savePath+separator + FOLDER_MODEL_A;
		String modelBDir = savePath+separator + FOLDER_MODEL_B;
		
		String resASavePath = modelADir+separator+resourceA_name;
		String resBSavePath = modelBDir+separator+resourceB_name;
		
		LogUtil.log(LogEvent.NOTICE, "serialize "+ resourceA_name + " to " + resASavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resASavePath), resourceA.getContents().get(0), true);
		
		if(!resourceADiag.getResources().isEmpty()){
			for(Resource resource : resourceADiag.getResources()){
				String resourceADiag_name = resource.getURI().lastSegment();
				String resADiagSavePath = modelADir+separator+resourceADiag_name;
				EMFStorage.eSaveAs(EMFStorage.pathToUri(resADiagSavePath), resource.getContents().get(0), false);
			}
		}
		
		
		LogUtil.log(LogEvent.NOTICE, "serialize "+ resourceB_name + " to " + resBSavePath);
		EMFStorage.eSaveAs(EMFStorage.pathToUri(resBSavePath), resourceB.getContents().get(0), true);
		
		if(!resourceBDiag.getResources().isEmpty()){
			for(Resource resource : resourceBDiag.getResources()){
				String resourceBDiag_name = resource.getURI().lastSegment();
				String resBDiagSavePath = modelBDir+separator+resourceBDiag_name;
				EMFStorage.eSaveAs(EMFStorage.pathToUri(resBDiagSavePath), resource.getContents().get(0), false);
			}
		}
		
		relativeResASavePath = EMFStorage.pathToRelativeUri(savePath, resASavePath).toString();
		relativeResBSavePath = EMFStorage.pathToRelativeUri(savePath, resBSavePath).toString();
		
		symmetricDifference.setUriModelA(relativeResASavePath);
		symmetricDifference.setUriModelB(relativeResBSavePath);
		}else{
			ISymbolicLinkHandler handler = settings.getSymbolicLinkHandler();
			symbolicLinksSet = handler.generateSymbolicLinks(asymmetricDifference, false);
			SymbolicLinkHandlerUtil.serializeSymbolicLinks(symbolicLinksSet, asymmetricDifference, savePath);
		}
		manifest_EditRules = new ArrayList<HashMap<String,String>>();
		for(RuleBase rb : asymmetricDifference.getRuleBases()){
			for(RuleBaseItem rbi : rb.getItems()){
				Module module = rbi.getEditRule().getExecuteModule();
				String erSavePath = savePath + separator + FOLDER_EDIT_RULES + separator + module.getName() + ".henshin";
				
				LogUtil.log(LogEvent.NOTICE, "serialize "+ rbi.getEditRule().getExecuteModule().getName() + " to " + erSavePath);
				EMFStorage.eSaveAs(EMFStorage.pathToUri(erSavePath), module, true);
				Module newMod = (Module)EMFStorage.eLoad(EMFStorage.pathToUri(erSavePath));
				rbi.getEditRule().setExecuteMainUnit(newMod.getUnit(INamingConventions.MAIN_UNIT));
				
				
				// MANIFEST
				String relSavePath = EMFStorage.pathToRelativeUri(savePath, erSavePath).toString();
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put("name", module.getName());
				attributes.put("href", relSavePath);
				attributes.put("version", rbi.getVersion());
				manifest_EditRules.add(attributes);
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
		manifest_Differences = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> symAtritbutes = new HashMap<String, String>();
		symAtritbutes.put("name", symmetricDiff_name);
		symAtritbutes.put("href", relativeSymDiffPath);
		manifest_Differences.add(symAtritbutes);
		HashMap<String, String> asymAtritbutes = new HashMap<String, String>();
		asymAtritbutes.put("name", asymmetricDiff_name);
		asymAtritbutes.put("href", relativeAsymDiffPath);
		manifest_Differences.add(asymAtritbutes);
		
		createManifest(savePath);
		
		// zip all necessary files
		ZipUtil.zip(savePath, savePath, AsymmetricDiffFacade.PATCH_EXTENSION);
		FileOperations.removeFolder(savePath);
		
		// Return path of saved zip:
		return savePath + "." + AsymmetricDiffFacade.PATCH_EXTENSION;
	}
	
	
	private void createManifest(String path) throws FileNotFoundException {
		manifest_Settings = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> conf_Matcher = new HashMap<String, String>();
		conf_Matcher.put("matcher", settings.getMatcher().getName());
		conf_Matcher.put("key", settings.getMatcher().getKey());
		manifest_Settings.add(conf_Matcher);
		
		if(settings.useSymbolicLinks()){
			HashMap<String, String> conf_SymbolicLinkHandler = new HashMap<String, String>();
			conf_SymbolicLinkHandler.put("symboliclinkhandler", settings.getSymbolicLinkHandler().getName());
			conf_SymbolicLinkHandler.put("key", settings.getSymbolicLinkHandler().getKey());
			manifest_Settings.add(conf_SymbolicLinkHandler);
		}
		
		XMLWriter writer = new XMLWriter(new FileOutputStream(new File(path+separator+"MANIFEST.xml")));
		writer.initDocument("manifest");
		if(settings.useSymbolicLinks()){
			char c = 'A';
			for(int i=0; i < symbolicLinksSet.size(); i++){
				String fileName = "LinksModel" + c + "." + SymbolicLinkHandlerUtil.SYMBOLIC_LINKS_EXT;
				createManifestElement(writer, "model"+c, "LinksModel"+c, fileName);
				c++;
			}
		}else{
			createManifestElement(writer, "modelA", resourceA_name, relativeResASavePath);
			createManifestElement(writer, "modelB", resourceB_name, relativeResBSavePath);
		}
		createManifestElement(writer, "editrules", "editrule", manifest_EditRules);;
		createManifestElement(writer, "differences", "difference", manifest_Differences);
		createManifestElement(writer, "settings", "setting", manifest_Settings);
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
