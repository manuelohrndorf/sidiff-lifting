package org.sidiff.editrule.generator.settings;

public class SergeSettings {

	private String outputFolderPath;
	private String configPath;
	
	/**
	 * This enables/disables subfolder creation/usage for different operation kinds
	 * (e.g. CREATE, DELETE)
	 */
	private boolean useSubfolders;
	/**
	 * This enables/disables the deletion of any existing contents in "manual" folders
	 */
	private boolean deleteManualTransformations;
	/**
	 * This enables/disables the deletion of previously generated transformations.
	 */
	private boolean deleteGeneratedTransformations;
	
	/**
	 * This enables/disables overwriting existing, generated transformations.
	 * Note: it will only enable/disable overwriting transformations which
	 * are produced during the current run with current configurations.
	 * It will not overwrite transformations, that have been produced with
	 * a different configuration (to delete those, use flag 'deleteGeneratedTransformations').
	 */
	private boolean overwriteGeneratedTransformations;
	/**
	 * This enables/disables the overwriting of previously saved config files
	 */
	private boolean overwriteConfigInTargetFolder;
	/**
	 * This enables/disables saving logs (e.g. inverse_module.log)
	 */
	private boolean saveLogs;
	/**
	 * This enables/disables the deletion of previous logs.
	 */
	private boolean deleteLogs;

	
	public SergeSettings(String outputFolderPath, String configPath){
		super();
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.useSubfolders = true;
		this.deleteManualTransformations = false;
		this.deleteGeneratedTransformations = true;
		this.overwriteGeneratedTransformations = true;
		this.overwriteConfigInTargetFolder = false;
		this.saveLogs = true;
		this.deleteLogs = true;
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
