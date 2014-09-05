package org.sidiff.difference.rulebase.extension;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.Trace;

/**
 * This interface belongs to the 'org.sidiff.difference.lifting.rulebase'
 * extension point. This extension point is used to add new rulebases to the
 * recognition engine. A plugin that adds this extension point has to implement
 * this interface.
 */
public interface IRuleBase {

	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.difference.rulebase.rulebase_extension";

	/**
	 * Returns the description name of the rulebase.
	 * 
	 * @return the rulebase name.
	 */
	public String getName();

	/**
	 * All document types the rulebase was generated for.
	 * 
	 * @return the rulebase document types.
	 */
	public Set<String> getDocumentTypes();

	/**
	 * Returns only the active rule base items of a rulebase.
	 * 
	 * 
	 * @return a set of rule base items.
	 */
	public Set<RuleBaseItem> getActiveRuleBaseItems();

	/**
	 * Returns all (inactive and active) rule base items of a rulebase.
	 * 
	 * 
	 * @return a set of rule base items.
	 */
	public Set<RuleBaseItem> getAllRuleBaseItems();

	/**
	 * Returns a list containing all active recognition rules (these are Henshin
	 * transformation "mainUnits") which will be applied by the recognition
	 * engine.
	 * 
	 * @return a list of recognition rules.
	 */
	public Set<Rule> getActiveRecognitionTransformationUnits();

	/**
	 * Returns the recognition rule (its a Henshin transformation "mainUnit")
	 * with the given name
	 * 
	 * @param name
	 *            of the recognition rule
	 * @return a recognition rule ({@link TransformationUnit})
	 */
	public Rule getRecognitionTransformationUnit(String name);

	/**
	 * Returns the corresponding recognition rule "mainUnit" for the given edit
	 * rule "mainUnit"
	 * 
	 * @param editRule
	 * @return the corresponding recognition rule ({@link TransformationUnit}).
	 */
	public Rule getRecognitionTransformationUnit(Unit editRule);

	/**
	 * Returns the corresponding edit rule "mainUnit" for the given recognition
	 * rule "mainUnit"
	 * 
	 * @param recognitionRule
	 * @return the corresponding edit rule ({@link TransformationUnit}).
	 */
	public Unit getEditRule(Rule recognitionRule);

	/**
	 * Returns a list containing all active rulebase edit rules
	 * 
	 * @return a list of edit rules ({@link EditRule}).
	 */
	public Set<EditRule> getActiveEditRules();

	/**
	 * Returns the edit rule with given name
	 * 
	 * @param name
	 *            of the edit rule
	 * @return an edit rule ({@link EditRule})
	 */
	public EditRule getEditRule(String name);

	/**
	 * Returns the trace from the given recognition rule node to the
	 * corresponding recognition rule node.
	 * 
	 * @param recognitionRuleNode
	 *            the recognition rule trace node.
	 * @return the corresponding {@link Trace} (Model A node of correspondence
	 *         pattern)
	 */
	public Trace getTraceA(Node recognitionRuleNode);

	/**
	 * Returns the trace from the given recognition rule node to the
	 * corresponding recognition rule node.
	 * 
	 * @param recognitionRuleNode
	 *            the recognition rule trace node.
	 * @return the corresponding {@link Trace} (Model B node of correspondence
	 *         pattern)
	 */
	public Trace getTraceB(Node recognitionRuleNode);

	/**
	 * Returns the Set of all PotentialDependencies potDep with
	 * potDep.sourceRule = editRule. If the given editRule isn't a source of any
	 * PotentialDependency, this method returns {@link Collections#EMPTY_SET}.
	 * 
	 * @param editRule
	 * @return
	 */
	public Set<PotentialDependency> getPotentialDependencies(EditRule editRule);

	/**
	 * Activates all RuleBaseItems of the rulebase.
	 */
	public void activateAllRuleBaseItems();

	/**
	 * Deactivates all RuleBaseItems of the rulebase.
	 */
	public void deactivateAllRuleBaseItems();

}
