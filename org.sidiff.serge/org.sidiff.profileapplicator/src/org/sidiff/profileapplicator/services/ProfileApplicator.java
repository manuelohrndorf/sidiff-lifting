package org.sidiff.profileapplicator.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

public class ProfileApplicator {

	private String inputFolderPath = null;
	private String configPath = null;
	private String outputFolderPath = null;

	private boolean metaInstances;
	private List<EPackage> basePackages = new ArrayList<EPackage>();
	private List<EPackage> stereoPackages = new ArrayList<EPackage>();

	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	
	
	public boolean applyProfile(){
		
		boolean result = true;
		
		//TODO
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

	// TODO

}
