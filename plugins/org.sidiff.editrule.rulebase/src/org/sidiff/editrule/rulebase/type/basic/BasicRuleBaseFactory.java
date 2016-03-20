package org.sidiff.editrule.rulebase.type.basic;

import org.sidiff.editrule.rulebase.type.IRuleBaseFactory;

public class BasicRuleBaseFactory implements IRuleBaseFactory<IBasicRuleBase> {

	@Override
	public Class<IBasicRuleBase> getRuleBaseType() {
		return IBasicRuleBase.TYPE;
	}

	@Override
	public IBasicRuleBase createRuleBase() {
		return new BasicRuleBase();
	}

}
