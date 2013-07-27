package org.sidiff.profileapplicator.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
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

public class ProfileApplicator {

	// Folder for input edit rules
	private String inputFolderPath = null;

	// Path for config file
	private String configPath = null;

	// Folder for output edit rules
	private String outputFolderPath = null;

	// Configuration parameters
	private boolean baseTypeInstances = false;
	private boolean baseTypeContext = false;

	// Profile configuration
	private String profileName = null;
	private EPackage basePackage;
	private EPackage stereoPackage;
	private List<URI> transformations = new ArrayList<URI>();
	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	/*
	 * Apply the profile to given input edit rules Configuration has already
	 * taken place in {@see ProfileApplicatorServiceImpl}
	 */
	public void applyProfile() {

		LogUtil.log(LogEvent.NOTICE,
				"Executing profile application for profile " + this.profileName
						+ "...");

		// For debugging purposes
		// Print used stereotypes without duplicates
		HashSet<String> stereoTypesUnique = new HashSet<String>();
		stereoTypesUnique.addAll(this.stereoTypes);
		LogUtil.log(LogEvent.NOTICE, "Using following stereotypes: "
				+ stereoTypesUnique);

		LogUtil.log(
				LogEvent.NOTICE,
				"Using following higher order transformations: "
						+ this.transformations
								.toString()
								.replace(
										"platform:/plugin/org.sidiff.profileapplicator/hots/",
										""));

		if (this.baseTypeInstances)
			LogUtil.log(
					LogEvent.NOTICE,
					"BaseTypeInstances allowed, source edit rules will also be copied untransformed.");

		if (this.baseTypeContext)
			LogUtil.log(LogEvent.NOTICE,
					"BaseTypeContext allowed, instances of baseType allowed as sufficient context");

		LogUtil.log(
				LogEvent.NOTICE,
				"Applying transformations now, this could (and most certainly will) take some time...");

		// Initialize organizing variables
		boolean stereoTypesUsed = false;
		String outputName = null;

		// Get all input henshin files
		File sourceFolder = new File(this.inputFolderPath);
		File[] sourceFiles = sourceFolder.listFiles();

		for (File sourceFile : sourceFiles) {

			// Input is really a henshin file
			if (sourceFile.getName().endsWith(".henshin")) {

				// Set appropriate output name
				outputName = this.outputFolderPath + sourceFile.getName();

				// Create resourceSet for source
				HenshinResourceSet srcResourceSet = new HenshinResourceSet(
						this.inputFolderPath);

				// Create EGraph for source
				EGraph srcGraph = new EGraphImpl(
						srcResourceSet.getResource(sourceFile.getName()));

				LogUtil.log(LogEvent.DEBUG,
						"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				LogUtil.log(LogEvent.DEBUG, "Transformating Editrule: "
						+ sourceFile.getName() + "...");
				LogUtil.log(LogEvent.DEBUG,
						"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				// variable to check if henshin rule has been successfully been
				// applied
				boolean applied = false;

				// Iterate over all stereoTypes used in profile
				for (int z = 0; z < this.stereoTypes.size(); z++) {

					LogUtil.log(LogEvent.DEBUG,
							"----------------------------------------------------------------------");
					LogUtil.log(LogEvent.DEBUG, "Applying Stereotype: "
							+ this.stereoTypes.get(z) + " to Basetype: "
							+ this.baseTypes.get(z));

					// Create resourceSet as working copy
					HenshinResourceSet workResourceSet = new HenshinResourceSet(
							this.inputFolderPath);

					// Create EGraph as working copy
					EGraph workGraph = new EGraphImpl(
							workResourceSet.getResource(sourceFile.getName()));

					// Add basePackage and stereoPackage to Graph for HOTs
					// matching
					workGraph.addTree(this.basePackage);
					workGraph.addTree(this.stereoPackage);

					// Create Henshin Engine
					Engine engine = new EngineImpl();

					// Iterate over all enabled higher order transformations
					for (URI hot : transformations) {

						// Create unitapplication for transformation
						UnitApplication unitapp = new UnitApplicationImpl(
								engine);
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
								this.stereoPackage.getNsURI());
						unitapp.setParameterValue("stereoType",
								this.stereoTypes.get(z));
						unitapp.setParameterValue("baseReference",
								this.baseReferences.get(z));
						unitapp.setParameterValue("baseType",
								this.baseTypes.get(z));

						// Execute henshin rule
						boolean executed = unitapp.execute(null);

						LogUtil.log(LogEvent.DEBUG, "Successfully applied: "
								+ executed);

						// If successfully executed set variables accordingly
						if (executed) {
							stereoTypesUsed = true;
							applied = true;
							outputName = this.outputFolderPath
									+ ((Module) workGraph.getRoots().get(0))
											.getName() + "_execute.henshin";

						}
						// If successfully executed
						// and baseTypeContext is set
						if (executed && this.baseTypeContext) {
							workResourceSet.saveEObject(workGraph.getRoots()
									.get(0), outputName);

							LogUtil.log(LogEvent.DEBUG,
									"Result saved as: "
											+ ((Module) workGraph.getRoots()
													.get(0)).getName()
											+ "_execute.henshin");

						}
						// "Free" resourceSet (Java GC does not think so)
						hotsResourceSet = null;

					}

					// Save created profiled henshin edit rule
					if (applied) {

						workResourceSet.saveEObject(
								workGraph.getRoots().get(0), outputName);

						LogUtil.log(
								LogEvent.DEBUG,
								"Result saved as: "
										+ ((Module) workGraph.getRoots().get(0))
												.getName() + "_execute.henshin");

						// Reset variable
						applied = false;
					}

					// Clear memory from unused EObjects
					// If not done, execution time is exponential
					workGraph.removeGraph(workGraph.getRoots().get(0));
					workResourceSet = null;

				}

				// Copy meta instances untransformed
				// if no stereotype could be executed on current
				// input rule or baseTypeInstances are allowed
				if (!stereoTypesUsed || this.baseTypeInstances) {

					srcResourceSet.saveEObject(srcGraph.getRoots().get(0),
							this.outputFolderPath + sourceFile.getName());

					LogUtil.log(
							LogEvent.DEBUG,
							"No applicable stereotype found or baseTypeInstances allowed, copied unmodified edit rule");

				}
				// Reset variable
				stereoTypesUsed = false;

				// Clear memory from unused EObjects
				// If not done, execution time is exponential
				srcGraph.removeGraph(srcGraph.getRoots().get(0));
				srcResourceSet = null;

			}

		}
		LogUtil.log(LogEvent.NOTICE,
				"Applying profile " + this.getProfileName() + " completed!");

	}

	/**
	 * @return the stereoTypes
	 */
	public List<String> getStereoTypes() {
		return stereoTypes;
	}

	/**
	 * @param stereoTypes
	 *            the stereoTypes to set
	 */
	public void setStereoTypes(List<String> stereoTypes) {
		this.stereoTypes = stereoTypes;
	}

	/**
	 * @return the baseTypes
	 */
	public List<String> getBaseTypes() {
		return baseTypes;
	}

	/**
	 * @param baseTypes
	 *            the baseTypes to set
	 */
	public void setBaseTypes(List<String> baseTypes) {
		this.baseTypes = baseTypes;
	}

	/**
	 * @return the baseReferences
	 */
	public List<String> getBaseReferences() {
		return baseReferences;
	}

	/**
	 * @param baseReferences
	 *            the baseReferences to set
	 */
	public void setBaseReferences(List<String> baseReferences) {
		this.baseReferences = baseReferences;
	}

	/**
	 * @param basePackage
	 *            the basePackage to set
	 */
	public void setBasePackage(EPackage basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * @param stereoPackage
	 *            the stereoPackage to set
	 */
	public void setStereoPackage(EPackage stereoPackage) {
		this.stereoPackage = stereoPackage;
	}

	/**
	 * @return the basePackage
	 */
	public EPackage getBasePackage() {
		return basePackage;
	}

	/**
	 * @return the stereoPackage
	 */
	public EPackage getStereoPackage() {
		return stereoPackage;
	}

	/**
	 * @return the inputFolderPath
	 */
	public String getInputFolderPath() {
		return inputFolderPath;
	}

	/**
	 * @param inputFolderPath
	 *            the inputFolderPath to set
	 */
	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}

	/**
	 * @return the inputConfigPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @param inputConfigPath
	 *            the inputConfigPath to set
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * @return the ouputFolderPath
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	/**
	 * @param ouputFolderPath
	 *            the ouputFolderPath to set
	 */
	public void setOutputFolderPath(String ouputFolderPath) {
		this.outputFolderPath = ouputFolderPath;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the baseTypeInstances
	 */
	public boolean isBaseTypeInstances() {
		return baseTypeInstances;
	}

	/**
	 * @param baseTypeInstances
	 *            the baseTypeInstances to set
	 */
	public void setBaseTypeInstances(boolean baseTypeInstances) {
		this.baseTypeInstances = baseTypeInstances;
	}

	/**
	 * @return the baseTypeContext
	 */
	public boolean isBaseTypeContext() {
		return baseTypeContext;
	}

	/**
	 * @param baseTypeContext
	 *            the baseTypeContext to set
	 */
	public void setBaseTypeContext(boolean baseTypeContext) {
		this.baseTypeContext = baseTypeContext;
	}

	/**
	 * @return the transformations
	 */
	public List<URI> getTransformations() {
		return transformations;
	}

	/**
	 * @param transformations
	 *            the transformations to set
	 */
	public void setTransformations(List<URI> transformations) {
		this.transformations = transformations;
	}

}
