package org.sidiff.editrule.rulebase.type.editrule;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.type.basic.IBasicRuleBase;

/**
 * This interface belongs to the edit-rule rulebase extension point. This
 * extension point is used to add new rulebases to the SiDiff framework. A
 * plug-in that adds this extension point has to implement this interface.
 */
public interface IEditRuleBase extends IBasicRuleBase {

	/**
	 * Used folder structure for projects with nature @link{RuleBaseProjectNature}	
	 */
	public static final String EDIT_RULE_FOLDER = "editrules";
	
	/**
	 * The registered rulebase type.
	 */
	public static Class<IEditRuleBase> TYPE = IEditRuleBase.class;
	
	/**
	 * Returns the edit rule with given name
	 * 
	 * @param name
	 *            of the edit rule
	 * @return an edit rule ({@link EditRule})
	 */
	public EditRule getEditRule(String name);

	/**
	 * Returns the Set of all PotentialDependencies potDep with
	 * potDep.sourceRule = editRule. If the given editRule isn't a source of any
	 * PotentialDependency, this method returns {@link Collections#EMPTY_SET}.
	 * 
	 * @param editRule
	 *            The source edit rules ({@link EditRule}).
	 * @return All corresponding potential dependencies.
	 */
	public Set<PotentialDependency> getPotentialDependencies(EditRule editRule);

	/**
	 * Returns a list containing all active rulebase edit rules
	 * 
	 * @return a list of edit rules ({@link EditRule}).
	 */
	public Set<EditRule> getActiveEditRules();
	
	/**
	 * Returns only the active edit rules of a rulebase.
	 * 
	 * @return a list of (henshin) edit rules.
	 */
	public Set<Unit> getActiveEditRuleMainUnits();
	
	/**
	 * Activates all edit rules of this rulebase.
	 */
	public void activateAllEditRules();

	/**
	 * Deactivates all edit rules of this rulebase.
	 */
	public void deactivateAllEditRules();

}
