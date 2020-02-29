package org.sidiff.difference.asymmetric.api.util;

import java.util.ArrayList;
import java.util.Set;

import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

public class RuleBaseFilter {

	private Set<IEditRuleBase> rulebases;
	private ArrayList<RuleBaseItem> changedItems = new ArrayList<>();

	public RuleBaseFilter(Set<IEditRuleBase> rulebases){
		this.rulebases = rulebases;
	}

	/**
	 * Disables all rules with derived references
	 */
	public void filterDerivedReferences() {
		for(IEditRuleBase rb : rulebases) {
			for(RuleBaseItem item : rb.getActiveRuleBaseItems()) {
				if(item.getEditRule().isUseDerivedFeatures()) {
					item.setActive(false);
					changedItems.add(item);					
				}
			}
		}
	}

	/**
	 * undoes all changes made by this class
	 */
	public void rollback() {
		for(RuleBaseItem item : changedItems) {
			item.setActive(true);
		}
	}
}
