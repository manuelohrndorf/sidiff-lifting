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

public class ProfileApplicator {

	private String hotsPath = "hots/";

	private String inputFolderPath = null;
	private String configPath = null;
	private String outputFolderPath = null;

	private boolean metaInstances;
	private List<EPackage> basePackages = new ArrayList<EPackage>();
	private List<EPackage> stereoPackages = new ArrayList<EPackage>();

	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	public void applyProfile() {

		HenshinResourceSet inputResourceSet = new HenshinResourceSet(
				this.inputFolderPath);
		File sourceFolder = new File(this.inputFolderPath);
		File[] sourceFiles = sourceFolder.listFiles();

		HenshinResourceSet hotsResourceSet = new HenshinResourceSet(
				this.hotsPath);
		File hotsFolder = new File(this.hotsPath);
		File[] hotsFiles = hotsFolder.listFiles();

		for (int i = 0; i < hotsFiles.length - 1; i++) {

			Module module = hotsResourceSet.getModule(hotsFiles[i].getName(),
					true);

			for (int l = 0; l < sourceFiles.length - 1; l++) {

				EGraph graph = new EGraphImpl(
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
				unitapp.setUnit((Unit) module.getUnit("mainUnit"));

				unitapp.setParameterValue("stereoPackage",
						this.stereoPackages.get(0));

				for (int z = 0; z < this.stereoTypes.size(); z++) {

					unitapp.setParameterValue("stereoType",
							this.stereoTypes.get(z));
					unitapp.setParameterValue("baseType", this.baseTypes.get(z));
					unitapp.setParameterValue("baseReference",
							this.baseReferences.get(z));

					System.out.println(unitapp.execute(null));
					inputResourceSet.saveEObject(
							graph.getRoots().get(0),
							outputFolderPath
									+ sourceFiles[i].getName().replace(
											this.baseTypes.get(z),
											this.stereoTypes.get(z)));
				}

			}
		}
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

	// TODO

}
