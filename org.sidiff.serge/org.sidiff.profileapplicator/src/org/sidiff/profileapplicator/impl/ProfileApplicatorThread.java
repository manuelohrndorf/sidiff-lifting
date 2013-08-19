package org.sidiff.profileapplicator.impl;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
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
import org.sidiff.profileapplicator.services.ProfileApplicator;

public class ProfileApplicatorThread extends Thread {

	/**
	 * The sourceFile to use as input.
	 */
	private File sourceFile;

	/**
	 * The defined {@link ProfileApplicator} which executed this thread The
	 * global configuration is just needed once and never changed by any of the
	 * threads, hence this solution
	 */
	private ProfileApplicator applicator;

	/**
	 * 
	 * Initialize profile applicator thread
	 * 
	 * @param sourcefiles
	 *            the source files for this thread
	 * @param applicator
	 *            the profile applicator which executed this thread
	 */
	public ProfileApplicatorThread(File sourceFile, ProfileApplicator applicator) {

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
		boolean baseTypeInstancesAllowed = false;
		String outputName = null;

		// Set appropriate output name
		outputName = applicator.getOutputFolderPath()
				+ this.sourceFile.getName();

		// Create resourceSet for source
		HenshinResourceSet srcResourceSet = new HenshinResourceSet(
				applicator.getInputFolderPath());

		// Create EGraph for source
		EGraph srcGraph = new EGraphImpl(srcResourceSet.getResource(sourceFile
				.getName()));

		LogUtil.log(LogEvent.NOTICE, "Transformating: " + sourceFile.getName()
				+ "...");

		// variable to check if henshin rule has been successfully been
		// applied
		boolean applied = false;

		// Iterate over all stereoTypes used in profile
		for (StereoType stereoType : applicator.getStereoTypes()) {

			// Read stereotype variable
			baseTypeInstancesAllowed = stereoType.isBaseTypeInstancesAllowed();

			for (String baseType : stereoType.getBaseTypeMap().keySet()) {

				LogUtil.log(LogEvent.DEBUG,
						"----------------------------------------------------------------------");
				LogUtil.log(LogEvent.DEBUG, "Applying Stereotype: "
						+ stereoType.getName() + " to Basetype: " + baseType);

				HenshinResourceSet workResourceSet = null;
				EGraph workGraph = null;

				try {
					// Create resourceSet as working copy
					workResourceSet = new HenshinResourceSet(
							applicator.getInputFolderPath());

					// Create EGraph as working copy
					workGraph = new EGraphImpl(
							workResourceSet.getResource(this.sourceFile
									.getName()));

					// Add basePackage and stereoPackage to Graph for HOTs
					// matching
					workGraph.addTree(applicator.getBasePackage());
					workGraph.addTree(applicator.getStereoPackage());
				} catch (Exception e) {

					// Nothing to do here
					// Just catching exceptions
					// of creating cross references

				}

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
					unitapp.setParameterValue("stereoPackage", applicator
							.getStereoPackage().getNsURI());
					unitapp.setParameterValue("stereoType",
							stereoType.getName());
					unitapp.setParameterValue("baseType", baseType);
					unitapp.setParameterValue("baseReference", stereoType
							.getBaseTypeMap().get(baseType));

					// Execute henshin rule
					boolean executed = unitapp.execute(null);

					LogUtil.log(LogEvent.DEBUG, "Successfully applied: "
							+ executed);

					// If successfully executed set variables accordingly
					if (executed) {
						stereoTypesUsed = true;
						applied = true;

						// Rename rule/module accordingly to profile
						String moduleName = ((Module) workGraph.getRoots().get(
								0)).getName();
						String moduleDescription = ((Module) workGraph
								.getRoots().get(0)).getDescription();
						String ruleName = ((Module) workGraph.getRoots().get(0))
								.getUnits().get(0).getName();
						String ruleDescription = ((Module) workGraph.getRoots()
								.get(0)).getUnits().get(0).getDescription();

						((Module) workGraph.getRoots().get(0))
								.setName(moduleName.replaceAll("_+" + baseType
										+ "_+", "_" + stereoType.getName()
										+ "(" + baseType + ")_"));
						((Module) workGraph.getRoots().get(0))
								.setDescription(moduleDescription.replaceAll(
										baseType, stereoType.getName() + "("
												+ baseType + ")"));

						((Module) workGraph.getRoots().get(0))
								.getUnits()
								.get(0)
								.setName(
										ruleName.replaceAll(baseType,
												stereoType.getName() + "("
														+ baseType + ")"));
						((Module) workGraph.getRoots().get(0))
								.getUnits()
								.get(0)
								.setDescription(
										ruleDescription.replaceAll(baseType,
												stereoType.getName() + "("
														+ baseType + ")"));

						outputName = applicator.getOutputFolderPath()
								+ ((Module) workGraph.getRoots().get(0))
										.getName() + "_execute.henshin";

					}
					// If successfully executed
					// and baseTypeContext is set
					if (executed && applicator.isBaseTypeContext()) {
						workResourceSet.saveEObject(
								workGraph.getRoots().get(0), outputName);

						LogUtil.log(
								LogEvent.DEBUG,
								"Result(with baseTypeContext) saved as: "
										+ ((Module) workGraph.getRoots().get(0))
												.getName() + "_execute.henshin");

					}
					// "Free" resourceSet (Java GC does not think so)
					hotsResourceSet = null;

				}

				// Save created profiled henshin edit rule
				if (applied) {

					workResourceSet.saveEObject(workGraph.getRoots().get(0),
							outputName);

					LogUtil.log(LogEvent.DEBUG, "Result saved as: "
							+ ((Module) workGraph.getRoots().get(0)).getName()
							+ "_execute.henshin");
					
					//Save unmodified version
					if(stereoType.isBaseTypeInstancesAllowed()){
						srcResourceSet.saveEObject(srcGraph.getRoots().get(0),
								applicator.getOutputFolderPath() + sourceFile.getName());
						LogUtil.log(LogEvent.DEBUG, "Saved unmodified source file: "
								+ ((Module) srcGraph.getRoots().get(0)).getName()
								+ "_execute.henshin");
					}

					// Reset variable
					applied = false;
				}

				try {
					// Clear memory from unused EObjects
					// If not done, execution time is exponential
					for (EObject roots : workGraph.getRoots()) {

						workGraph.removeGraph(roots);

					}
				} catch (Exception e) {

					// Nothing to do here
					// Just catching exceptions
					// of deleting cross references

				}
				workResourceSet = null;
				workGraph = null;

			}
		}

		// Copy meta instances untransformed
		// if no stereotype could be executed on current
		// input rule or baseTypeInstances are allowed
		if (!stereoTypesUsed || applicator.isBaseTypeInstances()) {

			srcResourceSet.saveEObject(srcGraph.getRoots().get(0),
					applicator.getOutputFolderPath() + sourceFile.getName());

			LogUtil.log(
					LogEvent.DEBUG,
					"No applicable stereotype found or baseTypeInstances allowed, copied unmodified edit rule");

		}
		// Reset variable
		stereoTypesUsed = false;

		try {
			// Clear memory from unused EObjects
			// If not done, execution time is exponential
			for (EObject roots : srcGraph.getRoots()) {

				srcGraph.removeGraph(roots);

			}
		} catch (Exception e) {

			// Nothing to do here
			// Just catching exceptions
			// of deleting cross references

		}
		srcResourceSet = null;
		srcGraph = null;

	}

}
