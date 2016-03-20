package org.sidiff.difference.rulebase.type;

import org.sidiff.editrule.rulebase.type.IRuleBaseFactory;

public class LiftingRuleBaseFactory implements IRuleBaseFactory<ILiftingRuleBase> {

	@Override
	public Class<ILiftingRuleBase> getRuleBaseType() {
		return ILiftingRuleBase.class;
	}

	@Override
	public ILiftingRuleBase createRuleBase() {
		return new LiftingRuleBase();
	}

}
