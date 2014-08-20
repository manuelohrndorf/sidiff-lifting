package org.sidiff.uml2v4.editrules.complex;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class UML_2v4_ComplexRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.uml2v4.editrules.complex";
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