package org.sidiff.difference.rulebase.view;

import org.sidiff.editrule.rulebase.view.IRuleBaseFactory;

public class LiftingRuleBaseFactory implements IRuleBaseFactory<ILiftingRuleBase> {

	@Override
	public Class<ILiftingRuleBase> getRuleBaseViewType() {
		return ILiftingRuleBase.class;
	}

	@Override
	public ILiftingRuleBase createRuleBase() {
		return new LiftingRuleBase();
	}

}
