package org.sidiff.ecore.recognitionrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class EcoreAtomicManualRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.ecore.recognitionrules.atomic";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "EcoreAtomicManual";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_DIR + "/" + RULE_BASE_NAME + RULE_BASE_EXT;
	}
}