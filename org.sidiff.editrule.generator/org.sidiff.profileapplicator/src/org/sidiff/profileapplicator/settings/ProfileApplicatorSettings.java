package org.sidiff.profileapplicator.settings;

public class ProfileApplicatorSettings {

	private String outputFolderPath;
	private String configPath;
	private String inputFolderPath;
	
	private int numberOfThreads;
	
	private boolean useSubfolders;
	private boolean deleteGeneratedTransformations;
	private boolean overwriteGeneratedTransformations;
	private boolean overwriteConfigInTargetFolder;
	
	
	public ProfileApplicatorSettings(String outputFolderPath,
			String configPath, String inputFolderPath) {
		super();
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.inputFolderPath = inputFolderPath;
		this.numberOfThreads = 1;
		this.useSubfolders = false;
		this.deleteGeneratedTransformations = false;
		this.overwriteGeneratedTransformations = false;
		this.overwriteConfigInTargetFolder = false;
	}


	public ProfileApplicatorSettings(String outputFolderPath,
			String configPath, String inputFolderPath, int numberOfThreads,
			boolean useSubfolders, boolean deleteGeneratedTransformations,
			boolean overwriteGeneratedTransformations,
			boolean overwriteConfigInTargetFolder) {
		super();
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.inputFolderPath = inputFolderPath;
		this.numberOfThreads = numberOfThreads;
		this.useSubfolders = useSubfolders;
		this.deleteGeneratedTransformations = deleteGeneratedTransformations;
		this.overwriteGeneratedTransformations = overwriteGeneratedTransformations;
		this.overwriteConfigInTargetFolder = overwriteConfigInTargetFolder;
	}


	public String getOutputFolderPath() {
		return outputFolderPath;
	}


	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}


	public String getConfigPath() {
		return configPath;
	}


	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}


	public String getInputFolderPath() {
		return inputFolderPath;
	}


	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}


	public int getNumberOfThreads() {
		return numberOfThreads;
	}


	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}


	public boolean isUseSubfolders() {
		return useSubfolders;
	}


	public void setUseSubfolders(boolean useSubfolders) {
		this.useSubfolders = useSubfolders;
	}


	public boolean isDeleteGeneratedTransformations() {
		return deleteGeneratedTransformations;
	}


	public void setDeleteGeneratedTransformations(
			boolean deleteGeneratedTransformations) {
		this.deleteGeneratedTransformations = deleteGeneratedTransformations;
	}


	public boolean isOverwriteGeneratedTransformations() {
		return overwriteGeneratedTransformations;
	}


	public void setOverwriteGeneratedTransformations(
			boolean overwriteGeneratedTransformations) {
		this.overwriteGeneratedTransformations = overwriteGeneratedTransformations;
	}


	public boolean isOverwriteConfigInTargetFolder() {
		return overwriteConfigInTargetFolder;
	}


	public void setOverwriteConfigInTargetFolder(
			boolean overwriteConfigInTargetFolder) {
		this.overwriteConfigInTargetFolder = overwriteConfigInTargetFolder;
	}
}
