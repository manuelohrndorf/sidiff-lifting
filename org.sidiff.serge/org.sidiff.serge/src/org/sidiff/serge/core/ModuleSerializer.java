package org.sidiff.serge.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.serge.configuration.Configuration;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.configuration.GlobalConstants;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.settings.SergeSettings;

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
	 * Constructor
	 * @param settings
	 */
	public ModuleSerializer (SergeSettings settings) {
		this.settings = settings;
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
			OperationType opType = findOutOperationType(module);
			
			switch(opType) {
				case CREATE:
					outputFolderPath += "CREATE" + System.getProperty("file.separator");
				break;
				case DELETE:
					outputFolderPath +="DELETE" + System.getProperty("file.separator");
				break;
				case MOVE:
					outputFolderPath +="MOVE" + System.getProperty("file.separator");
				break;
				case MOVE_REFERENCE_COMBINATION:
					outputFolderPath +="MOVE"  + System.getProperty("file.separator");
				break;
				case MOVE_UP:
					outputFolderPath +="MOVE" + System.getProperty("file.separator");
				break;
				case MOVE_DOWN:
					outputFolderPath +="MOVE"  + System.getProperty("file.separator");
				break;
				case SET_ATTRIBUTE:
					outputFolderPath +="SET" + System.getProperty("file.separator");
				break;
				case SET_REFERENCE:
					outputFolderPath +="SET" + System.getProperty("file.separator");
				break;
				case UNSET_ATTRIBUTE:
					outputFolderPath +="UNSET" + System.getProperty("file.separator");
				break;
				case UNSET_REFERENCE:
					outputFolderPath +="UNSET" + System.getProperty("file.separator");
				break;
				case CHANGE_LITERAL:
					outputFolderPath +="CHANGE" + System.getProperty("file.separator");
				break;
				case CHANGE_REFERENCE:
					outputFolderPath +="CHANGE" + System.getProperty("file.separator");
				break;
				case ADD:
					outputFolderPath +="ADD" + System.getProperty("file.separator");
				break;
				case REMOVE:
					outputFolderPath +="REMOVE" + System.getProperty("file.separator");
				break;
				default:
					throw new OperationTypeNotImplementedException(moduleFileName);
			}						
		}

		// assertions / checks
		checkModuleFileNameEquality(module, outputFolderPath+moduleFileName);
		checkMainUnitIsUnique(module);

		// create resource out of module and outputFileName
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileUri = URI.createFileURI(outputFolderPath+moduleFileName);
		Resource resource = resourceSet.createResource(fileUri);
		resource.getContents().add(module);

		// create option map for saving
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, true);

		try {
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
		for (Module module : allModules) {
				serialize(module);
		}
	}
	
	/**
	 * Finds out the OperationType of a module by its prefix.
	 * 
	 * @param module
	 * @return
	 * @throws OperationTypeNotImplementedException
	 */
	private OperationType findOutOperationType(Module module) throws OperationTypeNotImplementedException {
		OperationType opType = null;
		
		if(config.CREATE_CREATES && (
				(module.getName().startsWith(GlobalConstants.CREATE_prefix)
				|| module.getName().startsWith("create")))) {
			
			opType = OperationType.CREATE;
			
		}
		else if(config.CREATE_DELETES && (
				(module.getName().startsWith(GlobalConstants.DELETE_prefix)
				|| module.getName().startsWith("delete")))) {
			
			opType = OperationType.DELETE;
		}
		else if(config.CREATE_MOVES && (
				(module.getName().startsWith(GlobalConstants.MOVE_prefix)
				|| module.getName().startsWith("move")))) {

			opType = OperationType.MOVE;
			
		}
		else if(config.CREATE_MOVE_REFERENCE_COMBINATIONS && (
				(module.getName().startsWith(GlobalConstants.MOVE_REF_COMBI_prefix))
				|| module.getName().startsWith("move"))) {

			opType = OperationType.MOVE_REFERENCE_COMBINATION;
			
		}
		else if(config.CREATE_MOVE_UPS && (
				(module.getName().startsWith(GlobalConstants.MOVE_UP_prefix))
				|| module.getName().startsWith("move"))) {

			opType = OperationType.MOVE_UP;
			
		}
		else if(config.CREATE_MOVE_DOWNS && (
				(module.getName().startsWith(GlobalConstants.MOVE_DOWN_prefix))
				|| module.getName().startsWith("move"))) {

		}
		else if(config.CREATE_SET_ATTRIBUTES &&
				(module.getName().startsWith(GlobalConstants.SET_ATTRIBUTE_prefix)
				|| module.getName().startsWith("set"))) {
			
			opType = OperationType.SET_ATTRIBUTE;
			
		}
		else if(config.CREATE_SET_REFERENCES &&
				(module.getName().startsWith(GlobalConstants.SET_REFERENCE_prefix)
				|| module.getName().startsWith("set"))) {
			
			opType = OperationType.SET_REFERENCE;
			
		}
		else if(config.CREATE_UNSET_ATTRIBUTES &&
				(module.getName().startsWith(GlobalConstants.UNSET_ATTRIBUTE_prefix)
				|| module.getName().startsWith("unset"))) {
			
			opType = OperationType.UNSET_ATTRIBUTE;
			
		}
		else if(config.CREATE_UNSET_REFERENCES &&
				(module.getName().startsWith(GlobalConstants.UNSET_REFERENCE_prefix)
				|| module.getName().startsWith("unset"))) {
			
			opType = OperationType.UNSET_REFERENCE;
			
		}		
		else if(config.CREATE_CHANGE_LITERALS &&
				(module.getName().startsWith(GlobalConstants.CHANGE_LITERAL_prefix)
				|| module.getName().startsWith("change"))) {
			
			opType = OperationType.CHANGE_LITERAL;
			
		}
		else if(config.CREATE_CHANGE_REFERENCES &&
				(module.getName().startsWith(GlobalConstants.CHANGE_REFERENCE_prefix)
				|| module.getName().startsWith("change"))) {
			
			opType = OperationType.CHANGE_REFERENCE;
			
		}			
		else if(config.CREATE_ADDS &&
				(module.getName().startsWith(GlobalConstants.ADD_prefix)
				|| module.getName().startsWith("add"))) {
			
			opType = OperationType.ADD;
			
		}
		else if(config.CREATE_REMOVES &&
				(module.getName().startsWith(GlobalConstants.REMOVE_prefix)
				|| module.getName().startsWith("remove"))) {
			
			opType = OperationType.REMOVE;
			
		}	
		else{
			throw new OperationTypeNotImplementedException("Can't find out which OperationType this module is: "+module.getName());
		}
		return opType;
	}
	
}
