package org.sidiff.editrule.generator.serge.settings;

import org.sidiff.editrule.generator.serge.Serge;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.util.EditRuleGeneratorUtil;


public class SergeSettings extends EditRuleGenerationSettings{
	
	
	public SergeSettings(EditRuleGenerationSettings generalSettings) {
		
		super(generalSettings.getGenerator(),
			  generalSettings.getOutputFolderPath(),
			  generalSettings.getConfigPath(),
			  generalSettings.isUseDefaultConfig(),
			  generalSettings.getMetaModelNsUri(),
			  generalSettings.isUseSubfolders()
			  );
		
	}
	
	public SergeSettings(String outputFolderPath, String configPath, boolean useSubfolders){
		
		super(
				EditRuleGeneratorUtil.getEditRuleGeneratorByKey(Serge.GENERATOR_KEY),
				outputFolderPath,
				configPath,
				false,
				null,
				useSubfolders);
		
		this.deleteManualTransformations = false;
		this.deleteGeneratedTransformations = true;
		this.overwriteGeneratedTransformations = true;
		this.overwriteConfigInTargetFolder = true;
		this.saveLogs = false;
		this.deleteLogs = false;

	}
	
	public SergeSettings(String outputFolderPath, String configPath,
			boolean useSubfolders, boolean deleteManualTransformations,
			boolean deleteGeneratedTransformations,
			boolean overwriteGeneratedTransformations,
			boolean overwriteConfigInTargetFolder, boolean saveLogs,
			boolean deleteLogs) {
		
		super(
				EditRuleGeneratorUtil.getEditRuleGeneratorByKey("serge"),
				outputFolderPath, 
				configPath,
				false,
				null,
				useSubfolders);
		this.deleteManualTransformations = deleteManualTransformations;
		this.deleteGeneratedTransformations = deleteGeneratedTransformations;
		this.overwriteGeneratedTransformations = overwriteGeneratedTransformations;
		this.overwriteConfigInTargetFolder = overwriteConfigInTargetFolder;
		this.saveLogs = saveLogs;
		this.deleteLogs = deleteLogs;
	}

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
