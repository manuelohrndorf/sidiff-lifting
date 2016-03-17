package org.sidiff.editrule.rulebase.type.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

/**
 * Basic type specific rulebase wrapper implementation.
 */
public abstract class AbstractRuleBase implements IRuleBase {

	/**
	 * The rulebase instance.
	 */
	protected RuleBase rulebase;
	
	@Override
	public void init(RuleBase rulebase) {
		this.rulebase = rulebase;
	}
	
	@Override
	public RuleBase getRuleBase() {
		return rulebase;
	}
	
	@Override
	public Set<String> getDocumentTypes() {
		Set<String> docTypes = new HashSet<String>(getRuleBase().getDocumentTypes());
		return Collections.unmodifiableSet(docTypes);
	}
	
	@Override
	public Collection<RuleBaseItem> getActiveRuleBaseItems() {
		ArrayList<RuleBaseItem> active = new ArrayList<RuleBaseItem>(rulebase.getItems().size());
		
		for (RuleBaseItem item : rulebase.getItems()) {
			if (item.isActive()) {
				active.add(item);
			}
		}
		
		active.trimToSize();
		return active;
	}
}
