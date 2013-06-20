package org.sidiff.profileapplicator.services;



public class ProfileApplicator {
	
	private String inputFolderPath = null;
	private String ConfigPath = null;
	private String outputFolderPath = null;
	
	private boolean baseTypeOnly;
	private String[] basePackages;
	private String[] stereoPackages;
	
	
	/**
	 * @return the baseTypeOnly
	 */
	public boolean isBaseTypeOnly() {
		return baseTypeOnly;
	}
	/**
	 * @param baseTypeOnly the baseTypeOnly to set
	 */
	public void setBaseTypeOnly(boolean baseTypeOnly) {
		this.baseTypeOnly = baseTypeOnly;
	}
	/**
	 * @return the basePackages
	 */
	public String[] getBasePackages() {
		return basePackages;
	}
	/**
	 * @param basePackages the basePackages to set
	 */
	public void setBasePackages(String[] basePackages) {
		this.basePackages = basePackages;
	}
	/**
	 * @return the stereoPackages
	 */
	public String[] getStereoPackages() {
		return stereoPackages;
	}
	/**
	 * @param stereoPackages the stereoPackages to set
	 */
	public void setStereoPackages(String[] stereoPackages) {
		this.stereoPackages = stereoPackages;
	}
	/**
	 * @return the inputFolderPath
	 */
	public String getInputFolderPath() {
		return inputFolderPath;
	}
	/**
	 * @param inputFolderPath the inputFolderPath to set
	 */
	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}
	/**
	 * @return the inputConfigPath
	 */
	public String getConfigPath() {
		return ConfigPath;
	}
	/**
	 * @param inputConfigPath the inputConfigPath to set
	 */
	public void setConfigPath(String ConfigPath) {
		this.ConfigPath = ConfigPath;
	}
	/**
	 * @return the ouputFolderPath
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}
	/**
	 * @param ouputFolderPath the ouputFolderPath to set
	 */
	public void setOutputFolderPath(String ouputFolderPath) {
		this.outputFolderPath = ouputFolderPath;
	}
	
	
	//TODO

}
