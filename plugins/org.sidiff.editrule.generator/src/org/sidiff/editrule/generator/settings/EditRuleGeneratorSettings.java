package org.sidiff.editrule.generator.settings;

/**
 * Class for the concept of EditRuleGeneratorSettings
 * which configure an implementation of such a generator.
 * 
 * @author dreuling
 *
 */
public class EditRuleGeneratorSettings {
	
	/**
	 * The default folder path to write the generated EditRules to.
	 */
	private String outputFolderPath;
	
	/**
	 * The config path for configuring the generator in more detail.
	 */
	private String configPath;
	
	/**
	 * This enables/disables subfolder creation/usage for different @link{OperationType}.
	 * (e.g. CREATE, DELETE)
	 */
	private boolean useSubfolders;
	
	public EditRuleGeneratorSettings(String outputFolderPath, String configPath, Boolean useSubfolders){
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.useSubfolders = useSubfolders;
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


}
