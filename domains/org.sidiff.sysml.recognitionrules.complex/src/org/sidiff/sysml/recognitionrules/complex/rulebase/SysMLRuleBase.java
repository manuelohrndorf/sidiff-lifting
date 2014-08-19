package org.sidiff.sysml.recognitionrules.complex.rulebase;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

/**
 * Implementation of {@link IRuleBase} interface.
 * 
 * Rulebase with atomic recognition rules for the sysml metamodel.
 */
public class SysMLRuleBase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.sysml.recognitionrules.complex";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "SysMLComplex";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	public static final String EDIT_RULES_PLUGIN = "org.sidiff.sysml.editrules.complex";
	
	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_DIR + "/" + RULE_BASE_NAME + RULE_BASE_EXT;
	}

	@Override
	protected String getRecognitionRulesPluginID() {
		return BUNDLE_ID;
	}

	@Override
	protected String getEditRulesPluginID() {
		return EDIT_RULES_PLUGIN;
	}
}
