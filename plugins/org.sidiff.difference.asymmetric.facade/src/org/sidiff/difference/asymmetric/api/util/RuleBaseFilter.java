package org.sidiff.difference.asymmetric.api.util;

import java.util.ArrayList;
import java.util.Set;

import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.extension.IRuleBase;

public class RuleBaseFilter {
	
	private Set<IRuleBase> rulebases;
	private ArrayList <RuleBaseItem> changedItems = new ArrayList<RuleBaseItem>();
	
	public RuleBaseFilter(Set<IRuleBase> rulebases){
		this.rulebases = rulebases;
	}
	
	/**
	 * Disables all rules with derived references
	 */
	public void filterDerivedReferences(){
		for(IRuleBase rb : rulebases){
			for(RuleBaseItem item : rb.getActiveRuleBaseItems()){
				if(item.getEditRule().isUseDerivedFeatures())
					item.setActive(false);
					changedItems.add(item);
			}
		}
	}

	/**
	 * undoes all changes made by this class
	 */
	public void rollback(){
		for(RuleBaseItem item : changedItems){
			item.setActive(true);
		}
	}
}
