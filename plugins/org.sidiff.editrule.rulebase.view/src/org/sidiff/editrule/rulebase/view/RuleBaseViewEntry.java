package org.sidiff.editrule.rulebase.view;

import java.util.Set;

import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

public class RuleBaseViewEntry {

	private String id;
	private IRuleBaseFactory<?> factory;
	private Set<String> requiredAttachments;

	public RuleBaseViewEntry(String id, IRuleBaseFactory<?> factory, Set<String> requiredAttachments) {
		super();
		this.id = id;
		this.factory = factory;
		this.requiredAttachments = requiredAttachments;
	}

	public String getId() {
		return id;
	}

	public IRuleBaseFactory<?> getFactory() {
		return factory;
	}

	public Class<? extends IBasicRuleBase> getViewType() {
		return factory.getRuleBaseViewType();
	}
	public Set<String> getRequiredAttachments() {
		return requiredAttachments;
	}
}
