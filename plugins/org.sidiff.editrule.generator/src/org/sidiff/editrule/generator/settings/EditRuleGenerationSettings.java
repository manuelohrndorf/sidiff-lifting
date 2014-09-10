package org.sidiff.editrule.generator.settings;

import org.sidiff.editrule.generator.IEditRuleGenerator;

/**
 * Class for the concept of EditRuleGeneratorSettings
 * which configure an implementation of such a generator.
 * 
 * @author dreuling, mrindt
 *
 */
public class EditRuleGenerationSettings {
	
	/**
	 * The generator to use
	 */
	protected IEditRuleGenerator generator;
	
	/**
	 * The default folder path to write the generated EditRules to.
	 */
	protected String outputFolderPath;

	/**
	 * The config path for configuring the generator in more detail.
	 */
	protected String configPath;
	
	/**
	 * If a default config should be generated;
	 */
	protected boolean useDefaultConfig;	
	
	/**
	 * Namespace uri of a meta-model that was selected when choosing default config.
	 */
	protected String metaModelNsUri;
	
	/**
	 * This enables/disables subfolder creation/usage for different @link{OperationType}.
	 * (e.g. CREATE, DELETE)
	 */
	protected boolean useSubfolders;
	
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
	public boolean isUseDefaultConfig() {
		return useDefaultConfig;
	}

	public void setUseDefaultConfig(boolean useDefaultConfig) {
		this.useDefaultConfig = useDefaultConfig;
	}

	public String getMetaModelNsUri() {
		return metaModelNsUri;
	}

	public void setMetaModelNsUri(String metaModelNsUri) {
		this.metaModelNsUri = metaModelNsUri;
	}

}
