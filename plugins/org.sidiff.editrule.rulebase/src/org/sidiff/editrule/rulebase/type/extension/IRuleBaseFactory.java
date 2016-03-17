package org.sidiff.editrule.rulebase.type.extension;

/**
 * Creates a type specific rulebase wrapper instance.
 *
 * @param <R> The specific rulebase type.
 */
public interface IRuleBaseFactory<R extends IRuleBase> {

	/**
	 * @return The specific rulebase wrapper type.
	 */
	public Class<R> getRuleBaseType();
	
	/**
	 * @return A type specific rulebase wrapper instance.
	 */
	public R createRuleBase();
}
