package org.sidiff.editrule.rulebase.view.basic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

/**
 * Basic rulebase view implementation.
 */
public class BasicRuleBase implements IBasicRuleBase {

	/**
	 * The rulebase instance.
	 */
	protected RuleBase rulebase;
	
	@Override
	public void init(RuleBase rulebase) {
		this.rulebase = rulebase;
	}
	
	@Override
	public String getKey() {
		return getRuleBase().getKey();
	}
	
	@Override
	public String getName() {
		return getRuleBase().getName();
	}
	
	@Override
	public RuleBase getRuleBase() {
		return rulebase;
	}
	
	@Override
	public Set<String> getDocumentTypes() {
		return Collections.unmodifiableSet(new HashSet<String>(getRuleBase().getDocumentTypes()));
	}
	
	@Override
	public Collection<RuleBaseItem> getActiveRuleBaseItems() {
		return rulebase.getItems().stream()
			.filter(RuleBaseItem::isActive)
			.collect(Collectors.toList());
	}
}
