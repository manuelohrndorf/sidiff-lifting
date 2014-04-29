package org.sidiff.profileapplicator.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

public class ProfileApplicatorThread extends Thread {

	/**
	 * The sourceFile to use as input.
	 */
	private File sourceFile;

	/**
	 * The defined {@link Applicator} which executed this thread The
	 * global configuration is just needed once and never changed by any of the
	 * threads, hence this solution
	 */
	private Applicator applicator;

	/**
	 * 
	 * Initialize profile applicator thread
	 * 
	 * @param sourcefiles
	 *            the source files for this thread
	 * @param applicator
	 *            the profile applicator which executed this thread
	 */
	public ProfileApplicatorThread(File sourceFile, Applicator applicator) {

		this.sourceFile = sourceFile;
		this.applicator = applicator;

	}

	/**
	 * Thread run method
	 */
	public void run() {

		// Just execute applyProfile method
		applyProfile();

	}

	/**
	 * 
	 * The working method, which applies a profile to a given input file,
	 * whereas the input file is a henshin edit rule. Both are configured
	 * through the constructor call of this thread.
	 * 
	 */
	public void applyProfile() {

		// Initialize organizing variables
		boolean stereoTypesUsed = false;

		// Create resourceSet for source
		HenshinResourceSet srcResourceSet = new HenshinResourceSet(
				applicator.getInputFolderPath());

		// Create EGraph for source
		EGraph srcGraph = new EGraphImpl();
		srcGraph.add(srcResourceSet.getModule(sourceFile.getName()));

		LogUtil.log(LogEvent.NOTICE, "Transformating: " + sourceFile.getName()
				+ "...");

		// Iterate over all stereoTypes used in profile
		for (StereoType stereoType : applicator.getStereoTypes()) {

			// Remember if at least one stereoType has been used
			if (applyStereoType(stereoType))
				stereoTypesUsed = true;
		}

		if (applicator.isBaseTypeInstances() || !stereoTypesUsed) {
			LogUtil.log(
					LogEvent.DEBUG,
					"BaseTypeInstances allowed or stereoType not applicable"
							+ ", copied unmodified module to: "
							+ saveModule(srcResourceSet, srcGraph));
		}

		// Clear memory
		releaseAdapters(srcGraph);
		srcResourceSet = null;
		srcGraph = null;
	}

	/**
	 * Tries to apply a given stereoType to the input file, defined through
	 * Thread constructor. Returns true if stereoType could be applied
	 * successfully.
	 * 
	 * @param stereoType
	 *            stereoType to apply
	 * @return boolean if stereoType could be applied successfully
	 */
	public boolean applyStereoType(StereoType stereoType) {

		// Initialize organizing variables
		boolean stereoTypeUsed = false;

		for (EClass baseType : stereoType.getBaseTypeMap().keySet()) {

			boolean applied = false;

			LogUtil.log(LogEvent.DEBUG, "---------------------------");
			LogUtil.log(LogEvent.DEBUG,
					"Applying Stereotype: "
							+ stereoType.getName()
							+ " --("
							+ stereoType.getBaseTypeMap().get(baseType)
									.getName() + ")--> " + baseType.getName());

			HenshinResourceSet workResourceSet = null;
			EGraph workGraph = null;
			try {
				// Create resourceSet as working copy
				workResourceSet = new HenshinResourceSet(
						applicator.getInputFolderPath());

				// Create Module EGraph and its children as working copy
				workGraph = new EGraphImpl();
				workGraph.addTree(workResourceSet.getModule(this.sourceFile
						.getName()));

				// Add all important elements for matching
				workGraph.add(applicator.getStereoPackage());

				// Add Stereotype and its Attributes
				workGraph.add(stereoType.getStereoTypeClass());
				for (EStructuralFeature feature : stereoType
						.getStereoTypeClass().getEAllStructuralFeatures()) {
					workGraph.add(feature);
				}
				workGraph.add(baseType);
				workGraph.add(stereoType.getBaseTypeMap().get(baseType));

			} catch (Exception e) {

				// Nothing to do here
				// Just catching exceptions
				// of creating cross references

			}

			// Rename rule/module accordingly to profile
			// Naming scheme defined via editrule generator
			String baseTypeName = "_" + baseType.getName() + "_";
			String stereoTypeName = "_" + stereoType.getName() + "("
					+ baseType.getName() + ")" + "_";				

			// Just for perfomance purposes:
			// Check if modified module already exists in target folder
			Pattern baseTypePattern = Pattern.compile(baseTypeName);
			String moduleName = new String(((Module) workGraph.getRoots().get(0))
					.getName());
			Matcher m = baseTypePattern.matcher(moduleName); 
			moduleName = m.replaceAll(stereoTypeName);	
			
			// Test if result file already exists
			// Could be created by another thread or the previous run
			// -> No Matching/Transformation needed
			File targetFolder = new File(applicator.getOutputFolderPath());
			ArrayList<File> targetFiles = new ArrayList<File>(
					Arrays.asList(targetFolder.listFiles()));

			String outputName = applicator.getOutputFolderPath() + moduleName
					+ "_execute.henshin";

			boolean alreadyCreated = false;

			for (File targetFile : targetFiles) {

				if (targetFile.getAbsolutePath().equals(outputName)) {
					alreadyCreated = true;
					LogUtil.log(LogEvent.DEBUG,
							"File already created, skipped: " + outputName);

				}

			}

			// If target file has not been created before
			if (!alreadyCreated) {

				// Create Henshin Engine
				Engine engine = new EngineImpl();

				// Iterate over all enabled higher order transformations
				for (URI hot : applicator.getTransformations()) {

					// Create unitapplication for transformation
					UnitApplication unitapp = new UnitApplicationImpl(engine);
					// Use current working copy graph
					unitapp.setEGraph(workGraph);

					// Create resourceSet for higher order transformation
					// henshin rule
					HenshinResourceSet hotsResourceSet = new HenshinResourceSet();

					// Get module
					Module module = hotsResourceSet.getModule(hot, false);

					LogUtil.log(
							LogEvent.DEBUG,
							"Executing HOT "
									+ hot.toString()
											.replace(
													"platform:/plugin/org.sidiff.profileapplicator/hots/",
													"") + "...");

					// Set unit to SiLift default
					unitapp.setUnit((Unit) module.getUnit("mainUnit"));

					// setting parameters
					unitapp.setParameterValue("stereoPackage",
							applicator.getStereoPackage());
					unitapp.setParameterValue("stereoType",
							stereoType.getStereoTypeClass());
					unitapp.setParameterValue("stereoTypeName", stereoTypeName);
					unitapp.setParameterValue("baseType", baseType);
					unitapp.setParameterValue("baseTypeName", baseTypeName);
					unitapp.setParameterValue("baseReference", stereoType
							.getBaseTypeMap().get(baseType));

					boolean executed = false;

					// Execute Henshin unit as often as possible
					do {
						executed = unitapp.execute(null);
						// Keep track of successful execution
						if (executed) {
							stereoTypeUsed = true;
							applied = true;

						}
					} while (executed);

					LogUtil.log(LogEvent.DEBUG, "Successfully applied: "
							+ applied);

				}

				// Save created profiled henshin edit rule
				if (applied) {

					// Save module as new file
					LogUtil.log(LogEvent.DEBUG, "Result saved as: "
							+ saveModule(workResourceSet, workGraph));

				}
			}

			releaseAdapters(workGraph);
			workResourceSet = null;
			workGraph = null;

		}

		return stereoTypeUsed;

	}

	/**
	 * Helper method for saving a henshin module to a file according to its
	 * module name. Returns the filename of the save file.
	 * 
	 * @param resSet
	 *            ResourceSet containing the source graph
	 * @param graph
	 *            source graph
	 * @return filename of saved henshin file
	 */

	public String saveModule(HenshinResourceSet resSet, EGraph graph) {

		String outputName = applicator.getOutputFolderPath()
				+ ((Module) graph.getRoots().get(0)).getName()
				+ "_execute.henshin";
		resSet.saveEObject(graph.getRoots().get(0), outputName);

		return outputName;
	}

	/**
	 * Helper method for clearing adapters and memory leaks in EGraph
	 * 
	 * @param graph
	 *            The EGraph to clear of Adapters
	 */
	public void releaseAdapters(EGraph graph) {

		try {
			// Clear memory from unused EObjects
			// If not done, execution time is exponential
			for (EObject roots : graph.getRoots()) {

				graph.removeGraph(roots);
			}

		} catch (Exception e) {

			// Nothing to do here
			// Just catching exceptions
			// of deleting cross references

		}
	}

}
