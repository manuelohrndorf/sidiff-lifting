package org.sidiff.difference.rulebase.extension;

import org.osgi.framework.FrameworkUtil;

/**
 * Class for RuleBaseProjects in which the structure is statically defined.
 * 
 * This class can be used as default implementation in @link{RuleBaseProjectNature} projects.
 * 
 * @author dreuling
 *
 */
public abstract class AbstractProjectRuleBase extends AbstractRuleBase {
	
	// Used folder structure for projects with nature @link{RuleBaseProjectNature}	
	public static final String RULEBASE_FILE = "sidiff.rulebase";	
	public static final String BUILD_FOLDER = "recognitionrules";
	public static final String SOURCE_FOLDER = "editrules";

	@Override
	protected String getEditRulesPluginID() {
		return FrameworkUtil.getBundle(getClass()).getSymbolicName();
	}
	

	@Override
	protected String getRecognitionRulesPluginID() {
		return getEditRulesPluginID();
	}

	@Override
	protected String getRuleBaseURI() {
		return "/" + getEditRulesPluginID() + "/" + RULEBASE_FILE;
	}

}
