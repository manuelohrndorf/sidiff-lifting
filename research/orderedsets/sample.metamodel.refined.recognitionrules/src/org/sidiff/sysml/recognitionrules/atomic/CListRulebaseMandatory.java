package org.sidiff.sysml.recognitionrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class CListRulebaseMandatory extends AbstractRuleBase {

	public static final String BUNDLE_ID = "sample.metamodel.refined.recognitionrules";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "CListRulebaseMandatory";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	public static final String EDIT_RULES_PLUGIN = "sample.metamodel.refined.editrules";
	
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