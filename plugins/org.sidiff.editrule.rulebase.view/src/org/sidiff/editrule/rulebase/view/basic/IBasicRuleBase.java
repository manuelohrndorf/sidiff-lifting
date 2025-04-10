package org.sidiff.editrule.rulebase.view.basic;

import java.util.Collection;
import java.util.Set;

import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

/**
 * Minimal rulebase view interface.
 */
public interface IBasicRuleBase {

	/**
	 * The basic rulebase type.
	 */
	public static Class<IBasicRuleBase> VIEW_TYPE = IBasicRuleBase.class;
	
	/**
	 * @param rulebase A rulebase instance.
	 */
	void init(RuleBase rulebase);
	
	/**
	 * @return A unique key for this rulebase.
	 */
	public String getKey();
	
	/**
	 * @return A readable name for this rulebase.
	 */
	public String getName();
	
	/**
	 * @return The corresponding rulebase instance.
	 */
	public RuleBase getRuleBase();
	
	/**
	 * All document types the rulebase was created for.
	 * 
	 * @return the rulebase document types.
	 */
	public Set<String> getDocumentTypes();
	
	/**
	 * @return A list of all active rulebase items.
	 */
	public Collection<RuleBaseItem> getActiveRuleBaseItems();
}
