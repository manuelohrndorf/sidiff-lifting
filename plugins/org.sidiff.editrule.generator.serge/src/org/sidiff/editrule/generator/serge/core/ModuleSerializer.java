package org.sidiff.editrule.generator.serge.core;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.sidiff.editrule.generator.types.OperationType;

public class ModuleSerializer {

	/**
	 * The Settings.
	 */
	private SergeSettings settings = null;
	
	/**
	 * The Configuration.
	 */
	private Configuration config = Configuration.getInstance();
	
	/**
	 * FilenameFilter for *.henshin and *.henshin_diagram
	 */
	private FilenameFilter henshinFileNameFilter = null;
	
	/**
	 * Constructor
	 * @param settings
	 */
	public ModuleSerializer (SergeSettings settings) {

		this.settings = settings;
		
		// establishing henshin file extension filter
		henshinFileNameFilter = new FilenameFilter() {	
			@Override
			public boolean accept(File f, String fileName) {
				return fileName.endsWith(GlobalConstants.HENSHIN_EXT) || fileName.endsWith(GlobalConstants.HENSHIN_DIA_EXT);
			}
		};
		
	}
	
	
	/**
	 * Serializes one module.
	 * 
	 * @param module
	 * @throws OperationTypeNotImplementedException 
	 */
	private void serialize(Module module) throws OperationTypeNotImplementedException {

		String outputFolderPath = settings.getOutputFolderPath() + System.getProperty("file.separator") ;		
		String moduleFileName =  module.getName() + GlobalConstants.EXECUTE_suffix;
		
		if(settings.isUseSubfolders()) {
			outputFolderPath += findFittingSubfolderName(module);
		}
		
		// assertions / checks file name and module name equality and mainunit uniqueness
		checkModuleFileNameEquality(module, moduleFileName);
		checkMainUnitIsUnique(module);		

		// create resource out of module and outputFileName
		ResourceSet resourceSet = new ResourceSetImpl();
		
		URI fileUri = null;
		Path path = new Path(outputFolderPath);		
		
		// Check whether executed in Plug-In environment
		// or used with an absolute system file path
		if (path.isAbsolute())
			fileUri = URI.createFileURI(outputFolderPath+moduleFileName);
		else
			fileUri = URI.createPlatformResourceURI(outputFolderPath+moduleFileName,false);
		
		// check if file already exist and skip if overwriting is disabled
		if(!settings.isOverwriteGeneratedTransformations()) {
			File folder = new File(outputFolderPath);
			if(folder.isDirectory()) {
				for(File f:  folder.listFiles(henshinFileNameFilter)) {
					if(module.getName().concat(GlobalConstants.EXECUTE_suffix).equals(f.getName())) {
						LogUtil.log(LogEvent.NOTICE, "Already exists: " + module.getName()
								+ "  Overwriting was disabled. Thus serialization for this module will be skipped. ");
						return;
					}
				}
			}
		}
		
		// continue building resource for saving
		Resource resource = resourceSet.createResource(fileUri);
		resource.getContents().add(module);

		
		// create option map for saving
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
		
		//This is needed for URIs which may be using platform:/*
		options.put(XMIResource.OPTION_URI_HANDLER, new PlatformSchemeAware());

		try {
			LogUtil.log(LogEvent.NOTICE, "Save: " + resource.getURI());
			resource.save(options);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Checks whether module name and file name are equal dismissing the
	 * filename extension.
	 * 
	 * @param module
	 * @param outputFileName
	 */
	private void checkModuleFileNameEquality(Module module, String outputFileName) {

		// name equality assertion
		String name = outputFileName.replace(GlobalConstants.EXECUTE_suffix, "");
		name = name.replace(GlobalConstants.INITIALCHECK_suffix, "");
		String[] splitName = null;
		String separator = System.getProperty("file.separator");

		if (separator.equals("\\")) { // if Windows, prepend backslash to escape
			splitName = name.split("\\" + System.getProperty("file.separator"));
		} else {
			splitName = name.split(System.getProperty("file.separator"));
		}
		name = splitName[splitName.length - 1];
		assert (module.getName().equals(name)) : "Output file name and Module name are not equal.";

	}

	/**
	 * By convention, the main entry point for module execution must be named
	 * "mainUnit". This method checks whether there is exactly one "mainUnit".
	 * 
	 * @param module
	 */
	private void checkMainUnitIsUnique(Module module) {

		// Exactly one mainUnit assertion
		int mainUnitCount = 0;
		for (Unit unit : module.getUnits()) {
			if (!(unit instanceof Rule)) { // if it's a unit (seq, priority,
											// etc.)
				if (unit.getName().equals(INamingConventions.MAIN_UNIT)) {
					mainUnitCount++;
				}
				for (Unit subUnit : unit.getSubUnits(true)) {
					if (!(subUnit instanceof Rule)) { // if it's a unit (seq,
														// priority, etc.)
						if (subUnit.getName().equals(INamingConventions.MAIN_UNIT)) {
							mainUnitCount++;
						}

					}
				}
			}
		}
		assert (mainUnitCount == 1) : "Multiple or no main units in Module " + module.getName()
				+ ". Should be exactly one";
	}

	/**
	 * Convenience method to serialize multiple sets of modules.
	 * 
	 * @param allModules
	 * @throws OperationTypeNotImplementedException 
	 */
	public void serialize(Set<Module> allModules) throws OperationTypeNotImplementedException  {		
		
		
		// delete contents of manual folder if wished so
		if (settings.isDeleteManualTransformations()) {
			File manualFolder = new File(settings.getOutputFolderPath() + "manual");
			if (manualFolder.exists()) {
				for (File f : manualFolder.listFiles()) {
					f.delete();

				}
			}
		}
		
		// delete contents of generated folder if wished so
		if (settings.isDeleteGeneratedTransformations()) {
			//in case of "generated"-folder
			File generatedFolder = new File(settings.getOutputFolderPath() + "generated");
			if (generatedFolder.exists()) {
				for (File f : generatedFolder.listFiles()) {
					f.delete();

				}
			}else {
				//in case of no specific generated-folder
				File editrulesFolder = new File(settings.getOutputFolderPath());
				if(editrulesFolder.exists()) {
					deleteGeneratedFilesRecursivly(editrulesFolder);				
				}	
			}
		}
		
		// serialize each module
		for (Module module : allModules) {
				serialize(module);
		}
	}
	
	/**
	 * Finds out a fitting subfolder name for a module based on its name prefix.
	 * 
	 * @param module
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	private String findFittingSubfolderName(Module module) throws OperationTypeNotImplementedException {
		
		String expectedSubfolderName = "";
		
		if(config.isRuleCreationEnabled(OperationType.CREATE) && (
				(module.getName().startsWith(GlobalConstants.CREATE_prefix)
				|| module.getName().toLowerCase().startsWith("create")))) {
			
			expectedSubfolderName = "CREATE" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.DELETE) && (
				(module.getName().startsWith(GlobalConstants.DELETE_prefix)
				|| module.getName().toLowerCase().startsWith("delete")))) {
			
			expectedSubfolderName = "DELETE" + System.getProperty("file.separator");
		}
		else if(config.isRuleCreationEnabled(OperationType.ATTACH) &&
				(module.getName().startsWith(GlobalConstants.ATTACH_prefix)
				|| module.getName().toLowerCase().startsWith("attach"))) {
			
			expectedSubfolderName = "ATTACH" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.DETACH) &&
				(module.getName().startsWith(GlobalConstants.DETACH_prefix)
				|| module.getName().toLowerCase().startsWith("detach"))) {
			
			expectedSubfolderName = "DETACH" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.MOVE) && (
				(module.getName().startsWith(GlobalConstants.MOVE_prefix)
				|| module.getName().toLowerCase().startsWith("move")))) {

			expectedSubfolderName = "MOVE" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.MOVE_REFERENCE_COMBINATION) && (
				(module.getName().startsWith(GlobalConstants.MOVE_REF_COMBI_prefix))
				|| module.getName().toLowerCase().startsWith("move"))) {

			expectedSubfolderName = "MOVE" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.MOVE_UP) && (
				(module.getName().startsWith(GlobalConstants.MOVE_UP_prefix))
				|| module.getName().toLowerCase().startsWith("move"))) {

			expectedSubfolderName = "MOVE" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.MOVE_DOWN) && (
				(module.getName().startsWith(GlobalConstants.MOVE_DOWN_prefix))
				|| module.getName().toLowerCase().startsWith("move"))) {

			expectedSubfolderName = "MOVE" + System.getProperty("file.separator");
		}
		else if(config.isRuleCreationEnabled(OperationType.SET_ATTRIBUTE) &&
				(module.getName().startsWith(GlobalConstants.SET_ATTRIBUTE_prefix)
				|| module.getName().toLowerCase().startsWith("set"))) {
			
			expectedSubfolderName = "SET" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.SET_REFERENCE) &&
				(module.getName().startsWith(GlobalConstants.SET_REFERENCE_prefix)
				|| module.getName().toLowerCase().startsWith("set"))) {
			
			expectedSubfolderName = "SET" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.UNSET_ATTRIBUTE) &&
				(module.getName().startsWith(GlobalConstants.UNSET_ATTRIBUTE_prefix)
				|| module.getName().toLowerCase().startsWith("unset"))) {
			
			expectedSubfolderName = "UNSET" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.UNSET_REFERENCE) &&
				(module.getName().startsWith(GlobalConstants.UNSET_REFERENCE_prefix)
				|| module.getName().toLowerCase().startsWith("unset"))) {
			
			expectedSubfolderName = "UNSET" + System.getProperty("file.separator");
			
		}		
		else if(config.isRuleCreationEnabled(OperationType.CHANGE_LITERAL) &&
				(module.getName().startsWith(GlobalConstants.CHANGE_LITERAL_prefix)
				|| module.getName().toLowerCase().startsWith("change"))) {
			
			expectedSubfolderName = "CHANGE" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.CHANGE_REFERENCE) &&
				(module.getName().startsWith(GlobalConstants.CHANGE_REFERENCE_prefix)
				|| module.getName().toLowerCase().startsWith("change"))) {
			
			expectedSubfolderName = "CHANGE" + System.getProperty("file.separator");
			
		}			
		else if(config.isRuleCreationEnabled(OperationType.ADD) &&
				(module.getName().startsWith(GlobalConstants.ADD_prefix)
				|| module.getName().toLowerCase().startsWith("add"))) {
			
			expectedSubfolderName = "ADD" + System.getProperty("file.separator");
			
		}
		else if(config.isRuleCreationEnabled(OperationType.REMOVE) &&
				(module.getName().startsWith(GlobalConstants.REMOVE_prefix)
				|| module.getName().toLowerCase().startsWith("remove"))) {
			
			expectedSubfolderName = "REMOVE" + System.getProperty("file.separator");
			
		}	
		else{
			throw new OperationTypeNotImplementedException(OperationType.UNKNOWN);
		}
		return expectedSubfolderName;
	}
	
	/**
	 * This method helps to delete each henshin or henshinDiagram file in a folder and its sub folders
	 * but only if the folder is not named "manual".
	 * @param folder
	 */
	private void deleteGeneratedFilesRecursivly(File folder) {
		for(File f: folder.listFiles()) {
			if(f.isDirectory() && f.getName()!="manual") {
				deleteGeneratedFilesRecursivly(f);
			}else if (f.isFile() && (f.getName().endsWith("henshin") || f.getName().endsWith("henshinDiagram"))){
				f.delete();
			}
		}
	};
	
}
