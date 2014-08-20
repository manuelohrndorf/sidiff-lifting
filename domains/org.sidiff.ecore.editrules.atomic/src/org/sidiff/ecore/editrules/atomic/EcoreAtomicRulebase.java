package org.sidiff.ecore.editrules.atomic;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class EcoreAtomicRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.ecore.editrules.atomic";
	public static final String RULE_BASE_NAME = "sidiff.rulebase";
	
	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_NAME;
	}
	
	@Override
	protected String getRuleBasePluginID() {
		return BUNDLE_ID;
	}
}