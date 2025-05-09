package org.sidiff.editrule.generator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.WrongSettingsInstanceException;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;

public interface IEditRuleGenerator extends IExtension {

	Description<IEditRuleGenerator> DESCRIPTION = Description.of(IEditRuleGenerator.class,
			"org.sidiff.editrule.generator.generator_extension", "generator", "editrulegenerator");

	ExtensionManager<IEditRuleGenerator> MANAGER = new ExtensionManager<>(DESCRIPTION);


	/**
	 * Initialization method of the generator which makes use of
	 * an instance of @link{EditRuleGenerationSettings} to configure the generator.
	 * 
	 * @param settings The settings to use for configuration
	 * @param monitor ProgressMonitor to detect progress
	 * @throws EditRuleGenerationException 
	 * @throws WrongSettingsInstanceException 
	 */
	public void init(EditRuleGenerationSettings settings, IProgressMonitor monitor) throws EditRuleGenerationException, WrongSettingsInstanceException;

	/**
	 * Generate the EditRules. The configuration
	 * of output folder and other stuff has to be done in the
	 * 
	 * @param monitor ProgressMonitor to detect progress
	 */
    public void generateEditRules(IProgressMonitor monitor) throws EditRuleGenerationException;
}
