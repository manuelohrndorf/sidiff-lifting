package org.sidiff.serge.settings;

public class SergeSettings {

	private String outputFolderPath;
	private String configPath;
	
	private boolean useSubfolders;
	private boolean deleteManualTransformations;
	private boolean deleteGeneratedTransformations;
	private boolean overwriteGeneratedTransformations;
	private boolean overwriteConfigInTargetFolder;
	private boolean saveLogs;
	private boolean deleteLogs;
	
	public SergeSettings(String outputFolderPath, String configPath){
		super();
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.useSubfolders = false;
		this.deleteManualTransformations = false;
		this.deleteGeneratedTransformations = false;
		this.overwriteGeneratedTransformations = false;
		this.overwriteConfigInTargetFolder = false;
		this.saveLogs = true;
		this.deleteLogs = false;
	}
	
	public SergeSettings(String outputFolderPath, String configPath,
			boolean useSubfolders, boolean deleteManualTransformations,
			boolean deleteGeneratedTransformations,
			boolean overwriteGeneratedTransformations,
			boolean overwriteConfigInTargetFolder, boolean saveLogs,
			boolean deleteLogs) {
		super();
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.useSubfolders = useSubfolders;
		this.deleteManualTransformations = deleteManualTransformations;
		this.deleteGeneratedTransformations = deleteGeneratedTransformations;
		this.overwriteGeneratedTransformations = overwriteGeneratedTransformations;
		this.overwriteConfigInTargetFolder = overwriteConfigInTargetFolder;
		this.saveLogs = saveLogs;
		this.deleteLogs = deleteLogs;
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

	public boolean isUseSubfolders() {
		return useSubfolders;
	}

	public void setUseSubfolders(boolean useSubfolders) {
		this.useSubfolders = useSubfolders;
	}

	public boolean isDeleteManualTransformations() {
		return deleteManualTransformations;
	}

	public void setDeleteManualTransformations(boolean deleteManualTransformations) {
		this.deleteManualTransformations = deleteManualTransformations;
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

	public boolean isSaveLogs() {
		return saveLogs;
	}

	public void setSaveLogs(boolean saveLogs) {
		this.saveLogs = saveLogs;
	}

	public boolean isDeleteLogs() {
		return deleteLogs;
	}

	public void setDeleteLogs(boolean deleteLogs) {
		this.deleteLogs = deleteLogs;
	}
}
