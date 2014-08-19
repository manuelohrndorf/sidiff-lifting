package org.silift.fm.recognitionrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class FeatureModelAtomicGeneratedRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.silift.fm.recognitionrules.atomic";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "FeatureModelAtomicGenerated";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	public static final String EDIT_RULES_PLUGIN = "org.silift.fm.editrules.atomic";
	
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