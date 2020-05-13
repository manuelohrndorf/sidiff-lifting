package org.sidiff.editrule.rulebase.view.editrule;

import org.sidiff.editrule.rulebase.view.IRuleBaseFactory;

public class EditRuleBaseFactory implements IRuleBaseFactory<IEditRuleBase> {

	@Override
	public Class<IEditRuleBase> getRuleBaseViewType() {
		return IEditRuleBase.TYPE;
	}

	@Override
	public IEditRuleBase createRuleBase() {
		return new EditRuleBase();
	}
}
