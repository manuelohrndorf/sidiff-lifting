package org.sidiff.uml2v4.recognitionrules.complex;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class UML2ClassdiagramComplexRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.uml2v4.recognitionrules.complex";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "UML2ClassdiagramComplex";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	public static final String EDIT_RULES_PLUGIN = "org.sidiff.uml2v4.editrules.complex";
	
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