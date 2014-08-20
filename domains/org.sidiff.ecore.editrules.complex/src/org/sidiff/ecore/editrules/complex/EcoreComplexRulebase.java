package org.sidiff.ecore.editrules.complex;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class EcoreComplexRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.ecore.editrules.complex";
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