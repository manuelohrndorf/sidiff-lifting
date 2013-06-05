package org.sidiff.sysml.recognitionrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class SysMLAtomicManualRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.sysml.recognitionrules.atomic";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "SysMLAtomicManual";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_DIR + "/" + RULE_BASE_NAME + RULE_BASE_EXT;
	}
}