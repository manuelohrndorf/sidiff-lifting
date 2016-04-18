package org.sidiff.editrule.rulebase.view;

import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

/**
 * Creates a type specific rulebase wrapper instance.
 *
 * @param <R> The specific rulebase type.
 */
public interface IRuleBaseFactory<R extends IBasicRuleBase> {

	/**
	 * @return The specific rulebase wrapper type.
	 */
	public Class<R> getRuleBaseViewType();
	
	/**
	 * @return A type specific rulebase wrapper instance.
	 */
	public R createRuleBase();
}
