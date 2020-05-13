package org.sidiff.editrule.rulebase.view.basic;

import org.sidiff.editrule.rulebase.view.IRuleBaseFactory;

public class BasicRuleBaseFactory implements IRuleBaseFactory<IBasicRuleBase> {

	@Override
	public Class<IBasicRuleBase> getRuleBaseViewType() {
		return IBasicRuleBase.VIEW_TYPE;
	}

	@Override
	public IBasicRuleBase createRuleBase() {
		return new BasicRuleBase();
	}
}
