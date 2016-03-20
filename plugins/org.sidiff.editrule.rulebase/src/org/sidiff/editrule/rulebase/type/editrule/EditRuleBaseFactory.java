package org.sidiff.editrule.rulebase.type.editrule;

import org.sidiff.editrule.rulebase.type.IRuleBaseFactory;

public class EditRuleBaseFactory implements IRuleBaseFactory<IEditRuleBase> {

	@Override
	public Class<IEditRuleBase> getRuleBaseType() {
		return IEditRuleBase.TYPE;
	}

	@Override
	public IEditRuleBase createRuleBase() {
		return new EditRuleBase();
	}

}
