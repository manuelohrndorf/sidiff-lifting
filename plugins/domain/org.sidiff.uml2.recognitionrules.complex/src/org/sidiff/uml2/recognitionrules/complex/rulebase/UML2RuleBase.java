package org.sidiff.uml2.recognitionrules.complex.rulebase;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

/**
 * Implementation of {@link IRuleBase} interface.
 * 
 * Rulebase with atomic recognition rules for the UML2 metamodel.
 */
public class UML2RuleBase extends AbstractRuleBase {

	public static final String BUNDLE_ID = "org.sidiff.uml2.recognitionrules.complex";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "UML2_Complex";
	public static final String RULE_BASE_EXT = ".rulebase";

	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_DIR + "/" + RULE_BASE_NAME + RULE_BASE_EXT;
	}

}
