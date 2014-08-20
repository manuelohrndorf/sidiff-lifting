package org.sidiff.uml2v4.recognitionrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class UMLStateMachineAtomicGeneratedRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.uml2v4.recognitionrules.atomic";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "UMLStateMachineAtomicGenerated";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	public static final String EDIT_RULES_PLUGIN = "org.sidiff.uml2v4.editrules.atomic";
	
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