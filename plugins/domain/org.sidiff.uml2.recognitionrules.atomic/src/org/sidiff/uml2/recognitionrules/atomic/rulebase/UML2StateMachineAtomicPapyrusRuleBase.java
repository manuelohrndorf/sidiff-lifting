package org.sidiff.uml2.recognitionrules.atomic.rulebase;

import org.sidiff.difference.rulebase.extension.AbstractRuleBase;

/**
 * Implementation of {@link IRuleBase} interface.
 * 
 * Rulebase with atomic recognition rules for the UML2 StateMachine metamodel.
 */
public class UML2StateMachineAtomicPapyrusRuleBase extends AbstractRuleBase{

	public static final String BUNDLE_ID = "org.sidiff.uml2.recognitionrules.atomic";
	public static final String RULE_BASE_DIR = "rulebase";
	public static final String RULE_BASE_NAME = "UML2_StateMachine_AtomicPapyrus";
	public static final String RULE_BASE_EXT = ".rulebase";
	
	@Override
	protected String getRuleBaseURI() {
		return "/" + BUNDLE_ID + "/" + RULE_BASE_DIR + "/" + RULE_BASE_NAME + RULE_BASE_EXT;
	}

}
