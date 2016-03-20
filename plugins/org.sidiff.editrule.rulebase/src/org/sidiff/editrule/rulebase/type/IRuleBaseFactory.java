package org.sidiff.editrule.rulebase.type;

import org.sidiff.editrule.rulebase.type.basic.IBasicRuleBase;

/**
 * Creates a type specific rulebase wrapper instance.
 *
 * @param <R> The specific rulebase type.
 */
public interface IRuleBaseFactory<R extends IBasicRuleBase> {

	/**
	 * @return The specific rulebase wrapper type.
	 */
	public Class<R> getRuleBaseType();
	
	/**
	 * @return A type specific rulebase wrapper instance.
	 */
	public R createRuleBase();
}
