package org.sidiff.profileapplicator.services;

import java.io.File;
import java.util.ArrayList;
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

	private String inputFolderPath = null;
	private String configPath = null;
	private String outputFolderPath = null;

	private boolean debugOutput = false;
	private String profileName = null;
	private boolean baseTypeInstances = false;
	private boolean baseTypeContext = false;
	private EPackage basePackage;
	private EPackage stereoPackage;

	private List<URI> transformations = new ArrayList<URI>();

	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	public void applyProfile() {

		LogUtil.log(LogEvent.NOTICE,
				"Executing profile application for profile " + this.profileName
						+ "...");

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

		File sourceFolder = new File(this.inputFolderPath);
		File[] sourceFiles = sourceFolder.listFiles();

		boolean stereoTypesUsed = false;
		String outputName = null;

		EGraph srcGraph = null;

		Engine engine = new EngineImpl();

		HenshinResourceSet srcResourceSet = new HenshinResourceSet(
				this.inputFolderPath);

		HenshinResourceSet inputResourceSet = new HenshinResourceSet(
				this.inputFolderPath);

		HenshinResourceSet hotsResourceSet = new HenshinResourceSet();

		for (File sourceFile : sourceFiles) {

			if (sourceFile.getName().endsWith(".henshin")) {

				srcGraph = new EGraphImpl(srcResourceSet.getResource(sourceFile
						.getName()));

				if (this.debugOutput) {
					LogUtil.log(LogEvent.NOTICE,
							"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					LogUtil.log(LogEvent.NOTICE, "Transformating Editrule: "
							+ sourceFile.getName() + "...");
					LogUtil.log(LogEvent.NOTICE,
							"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				}

				boolean applied = false;
				outputName = this.outputFolderPath + sourceFile.getName();

				for (int z = 0; z < this.stereoTypes.size(); z++) {

					if (this.debugOutput) {
						LogUtil.log(LogEvent.NOTICE,
								"----------------------------------------------------------------------");
						LogUtil.log(LogEvent.NOTICE, "Applying Stereotype: "
								+ this.stereoTypes.get(z) + " to Basetype: "
								+ this.baseTypes.get(z));
					}

					EGraph workGraph = new EGraphImpl(
							inputResourceSet.getResource(sourceFile.getName()));

					workGraph.addTree(this.basePackage);
					workGraph.addTree(this.stereoPackage);

					for (URI hot : transformations) {

						UnitApplication unitapp = new UnitApplicationImpl(
								engine);
						unitapp.setEGraph(workGraph);

						Module module = hotsResourceSet.getModule(hot, false);

						if (this.debugOutput) {
							LogUtil.log(
									LogEvent.NOTICE,
									"Executing HOT "
											+ hot.toString()
													.replace(
															"platform:/plugin/org.sidiff.profileapplicator/hots/",
															"") + "...");
						}

						unitapp.setUnit((Unit) module.getUnit("mainUnit"));

						// hard coded
						unitapp.setParameterValue("stereoPackage",
								this.stereoPackage.getNsURI());

						// setting parameters
						unitapp.setParameterValue("stereoType",
								this.stereoTypes.get(z));
						unitapp.setParameterValue("baseReference",
								this.baseReferences.get(z));
						unitapp.setParameterValue("baseType",
								this.baseTypes.get(z));

						boolean executed = unitapp.execute(null);

						if (this.debugOutput) {
							LogUtil.log(LogEvent.NOTICE,
									"Successfully applied: " + executed);
						}
						if (executed) {
							stereoTypesUsed = true;
							applied = true;
							outputName = this.outputFolderPath
									+ ((Module) workGraph.getRoots().get(0))
											.getName() + "_execute.henshin";

						}
						if (executed && this.baseTypeContext) {
							inputResourceSet.saveEObject(workGraph.getRoots()
									.get(0), outputName);
							if (this.debugOutput) {
								LogUtil.log(
										LogEvent.NOTICE,
										"Result saved as: "
												+ ((Module) workGraph
														.getRoots().get(0))
														.getName()
												+ "_execute.henshin");
							}

						}

					}

					if (applied) {

						inputResourceSet.saveEObject(workGraph.getRoots()
								.get(0), outputName);
						if (this.debugOutput) {
							LogUtil.log(LogEvent.NOTICE,
									"Result saved as: "
											+ ((Module) workGraph.getRoots()
													.get(0)).getName()
											+ "_execute.henshin");

						}
						applied = false;
					}

				}
				// Copy meta instances untransformed
				if (!stereoTypesUsed || this.baseTypeInstances) {

					inputResourceSet.saveEObject(srcGraph.getRoots().get(0),
							this.outputFolderPath + sourceFile.getName());
					if (this.debugOutput) {
						LogUtil.log(
								LogEvent.NOTICE,
								"No applicable stereotype found or baseTypeInstances allowed, copied unmodified edit rule");
					}
				}
				stereoTypesUsed = false;

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
	 * @return the debugOutput
	 */
	public boolean isDebugOutput() {
		return debugOutput;
	}

	/**
	 * @param debugOutput
	 *            the debugOutput to set
	 */
	public void setDebugOutput(boolean debugOutput) {
		this.debugOutput = debugOutput;
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
