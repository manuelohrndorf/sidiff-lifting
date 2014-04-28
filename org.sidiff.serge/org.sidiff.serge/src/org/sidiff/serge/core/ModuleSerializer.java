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
import org.sidiff.serge.configuration.GlobalConstants;

public class ModuleSerializer {

	/**
	 * Serializes one module.
	 * 
	 * @param module
	 */
	private void serialize(Module module) {

		String outputFilePath = Configuration.getInstance().OUTPUTFOLDERPATH + module.getName()
				+ GlobalConstants.EXECUTE_suffix;

		// assertions / checks
		checkModuleFileNameEquality(module, outputFilePath);
		checkMainUnitIsUnique(module);

		// create resource out of module and outputFileName
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileUri = URI.createFileURI(outputFilePath);
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
	 */
	public void serialize(Set<Module> allModules) {
		for (Module module : allModules) {
			serialize(module);
		}
	}
}
