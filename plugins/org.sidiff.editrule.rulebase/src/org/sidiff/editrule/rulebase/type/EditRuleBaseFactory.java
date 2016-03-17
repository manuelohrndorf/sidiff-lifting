package org.sidiff.editrule.rulebase.type;

import org.sidiff.editrule.rulebase.type.extension.IRuleBaseFactory;

public class EditRuleBaseFactory implements IRuleBaseFactory<IEditRuleBase> {

	@Override
	public Class<IEditRuleBase> getRuleBaseType() {
		return IEditRuleBase.class;
	}

	@Override
	public IEditRuleBase createRuleBase() {
		return new EditRuleBase();
	}

}
