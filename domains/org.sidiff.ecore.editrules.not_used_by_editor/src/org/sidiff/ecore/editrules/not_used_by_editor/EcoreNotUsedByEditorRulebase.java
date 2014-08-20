package org.sidiff.ecore.editrules.not_used_by_editor;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

public class EcoreNotUsedByEditorRulebase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.ecore.editrules.not_used_by_editor";
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