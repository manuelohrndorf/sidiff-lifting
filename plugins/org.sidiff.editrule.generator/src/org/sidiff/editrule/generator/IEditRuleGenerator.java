package org.sidiff.editrule.generator;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.emf.exceptions.EAttributeNotFoundException;
import org.sidiff.common.emf.exceptions.EClassifierUnresolvableException;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.editrule.generator.exceptions.EditRuleGenerationException;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;


public interface IEditRuleGenerator {

	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.editrule.generator.generator_extension";
	
	/**
	 * Returns the description name of the generator.
	 * 
	 * @return the generator name.
	 */
	public String getName();

	/**
	 * Returns the short name (used as a key) of the generator.
	 * 
	 * @return the generator short name (used as key).
	 */
	public String getKey();

	/**
	 * Initialization method of the generator which makes use of
	 * an instance of @link{EditRuleGenerationSettings} to configure the generator.
	 * 
	 * @param settings The settings to use for configuration
	 * @param monitor ProgressMonitor to detect progress
	 * @throws EditRuleGenerationException 
	 */
	public void init(EditRuleGenerationSettings settings, IProgressMonitor monitor) throws EditRuleGenerationException;
	
	/**
	 * Generate the EditRules. The configuration
	 * of output folder and other stuff has to be done in the
	 * 
	 * @param monitor ProgressMonitor to detect progress
	 */
    public void generateEditRules(IProgressMonitor monitor) throws EditRuleGenerationException;
	

}
