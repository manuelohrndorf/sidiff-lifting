package org.sidiff.editrule.generator.settings;

import org.sidiff.editrule.generator.IEditRuleGenerator;

/**
 * Class for the concept of EditRuleGeneratorSettings
 * which configure an implementation of such a generator.
 * 
 * @author dreuling
 *
 */
public class EditRuleGenerationSettings {
	
	/**
	 * The generator to use
	 */
	private IEditRuleGenerator generator;
	
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
	
	public EditRuleGenerationSettings(IEditRuleGenerator generator,String outputFolderPath, String configPath, Boolean useSubfolders){
		this.generator = generator;
		this.outputFolderPath = outputFolderPath;
		this.configPath = configPath;
		this.useSubfolders = useSubfolders;
	}
	
	public IEditRuleGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(IEditRuleGenerator generator) {
		this.generator = generator;
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
