package org.sidiff.profileapplicator.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	// TODO
	/*
	 * 
	 * Better structure Remove imports/plugin dependencies/launch
	 * parameterplugin frozen model fix support all hots and stereoTypes check
	 * for correctness
	 */

	private String hotsPath = null;
	private String inputFolderPath = null;
	private String configPath = null;
	private String outputFolderPath = null;

	private String profileName = null;
	private boolean metaInstances = false;
	private List<EPackage> basePackages = new ArrayList<EPackage>();
	private List<EPackage> stereoPackages = new ArrayList<EPackage>();

	private List<String> transformations = new ArrayList<String>();

	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	public void applyProfile() {

		LogUtil.log(LogEvent.NOTICE,
				"Executing profile application for profile " + this.profileName
						+ "...");

		LogUtil.log(LogEvent.NOTICE,
				"Using following higher order transformations: "
						+ this.transformations.toString());

		if (this.metaInstances)
			System.out
					.println("MetaInstances allowed, will also copy source edit rules into target folder.");

		LogUtil.log(LogEvent.NOTICE,
				"Applying transformations now, this could take some time...");

		File sourceFolder = new File(this.inputFolderPath);
		File[] sourceFiles = sourceFolder.listFiles();

		File hotsFolder = new File(this.hotsPath);
		File[] hotsFiles = hotsFolder.listFiles();

		boolean stereoTypesUsed = false;
		String outputName = null;

		EGraph graph = null;
		HenshinResourceSet inputResourceSet = new HenshinResourceSet(
				this.inputFolderPath);
		HenshinResourceSet hotsResourceSet = new HenshinResourceSet(
				this.hotsPath);

		for (int l = 0; l < sourceFiles.length; l++) {

			if (sourceFiles[l].getName().endsWith(".henshin")) {
				
				
				LogUtil.log(LogEvent.NOTICE, "Transformating Editrule: "
						+ sourceFiles[l] + "...");

				graph = new EGraphImpl(
						inputResourceSet.getResource(sourceFiles[l].getName()));

				for (int k = 0; k < this.basePackages.size(); k++) {
					graph.addTree(this.basePackages.get(k));
				}

				for (int j = 0; j < this.stereoPackages.size(); j++) {
					graph.addTree(this.stereoPackages.get(j));
				}

				// Create an engine and a rule application:
				Engine engine = new EngineImpl();

				UnitApplication unitapp = new UnitApplicationImpl(engine);
				unitapp.setEGraph(graph);

				for (int z = 0; z < this.stereoTypes.size(); z++) {
					
					boolean applied = false;
					outputName = this.outputFolderPath
							+ sourceFiles[l].getName();					
					

					LogUtil.log(LogEvent.NOTICE, "Applying Stereotype: "
							+ this.stereoTypes.get(z) + "...");

					for (int i = 0; i < hotsFiles.length; i++) {

						if (hotsFiles[i].getName().endsWith(".henshin")
								&& useTransformation(hotsFiles[i].getName())) {
							Module module = hotsResourceSet.getModule(
									hotsFiles[i].getAbsolutePath(), false);

							LogUtil.log(LogEvent.NOTICE, "Executing HOT: "
									+ hotsFiles[i].getName() + "...");

							unitapp.setUnit((Unit) module.getUnit("mainUnit"));

							// hard coded
							unitapp.setParameterValue("stereoPackage",
									this.stereoPackages.get(0).getNsURI());

							// setting parameters
							unitapp.setParameterValue("stereoType",
									this.stereoTypes.get(z));
							unitapp.setParameterValue("baseType",
									this.baseTypes.get(z));
							unitapp.setParameterValue("baseReference",
									this.baseReferences.get(z));

							boolean executed = unitapp.execute(null);

							LogUtil.log(LogEvent.NOTICE,
									"Successfully applied: " + executed);
							if (executed) {
								stereoTypesUsed = true;
								applied = true;
								outputName = outputName.replace(this.baseTypes.get(z),this.stereoTypes.get(z));
								//outputName = outputName.replace("execute", this.stereoTypes.get(z)+ "execute");

							}

						}

					}					

					if (applied) {

						LogUtil.log(LogEvent.NOTICE, "graph: " + graph.getRoots());	
						inputResourceSet.saveEObject(graph.getRoots().get(0),
								outputName);
						LogUtil.log(LogEvent.NOTICE, "Result saved to: "
								+ outputName);		
						applied = false;
					}

				}
				// Copy meta instances untransformed
				if (!stereoTypesUsed || this.metaInstances) {
					
					inputResourceSet.saveEObject(graph.getRoots().get(0),
							this.outputFolderPath + sourceFiles[l].getName());
					LogUtil.log(LogEvent.NOTICE,
							"No applicable stereotype found, copied source ER");
					stereoTypesUsed = false;
				}

			}

		}
		LogUtil.log(LogEvent.NOTICE,
				"Applying profile " + this.getProfileName() + " completed!");

	}

	public boolean useTransformation(String transformation) {

		boolean result = false;

		for (int i = 0; i < this.transformations.size(); i++) {
			if (transformation.startsWith(this.transformations.get(i)))
				result = true;

		}

		return result;

	}

	/**
	 * @return the metaInstances
	 */
	public boolean isMetaInstances() {
		return metaInstances;
	}

	/**
	 * @param metaInstances
	 *            the metaInstances to set
	 */
	public void setMetaInstances(boolean metaInstances) {
		this.metaInstances = metaInstances;
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
	 * @param basePackages
	 *            the basePackages to set
	 */
	public void setBasePackages(List<EPackage> basePackages) {
		this.basePackages = basePackages;
	}

	/**
	 * @param stereoPackages
	 *            the stereoPackages to set
	 */
	public void setStereoPackages(List<EPackage> stereoPackages) {
		this.stereoPackages = stereoPackages;
	}

	/**
	 * @return the metaInstances
	 */
	public boolean ismetaInstances() {
		return metaInstances;
	}

	/**
	 * @param metaInstances
	 *            the metaInstances to set
	 */
	public void setmetaInstances(boolean metaInstances) {
		this.metaInstances = metaInstances;
	}

	/**
	 * @return the basePackages
	 */
	public List<EPackage> getBasePackages() {
		return basePackages;
	}

	/**
	 * @return the stereoPackages
	 */
	public List<EPackage> getStereoPackages() {
		return stereoPackages;
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
	 * @return the hotsPath
	 */
	public String getHotsPath() {
		return hotsPath;
	}

	/**
	 * @param hotsPath
	 *            the hotsPath to set
	 */
	public void setHotsPath(String hotsPath) {
		this.hotsPath = hotsPath;
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
	 * @return the transformations
	 */
	public List<String> getTransformations() {
		return transformations;
	}

	/**
	 * @param transformations
	 *            the transformations to set
	 */
	public void setTransformations(List<String> transformations) {
		this.transformations = transformations;
	}

	// TODO

}
