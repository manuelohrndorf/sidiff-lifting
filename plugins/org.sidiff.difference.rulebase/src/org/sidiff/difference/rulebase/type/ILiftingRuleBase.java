package org.sidiff.difference.rulebase.type;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.editrule.rulebase.type.IEditRuleBase;

/**
 * This interface belongs to the recognition-rule rulebase extension point. This
 * extension point is used to add new rulebases to the SiLift framework. A
 * plug-in that adds this extension point has to implement this interface.
 */
public interface ILiftingRuleBase extends IEditRuleBase {
	
	/** 
	 * Used folder structure for projects with nature @link{RuleBaseProjectNature}	
	 */
	public static final String RECOGNITION_RULE_FOLDER = "recognitionrules";
	
	/**
	 * The registered rulebase type.
	 */
	public static Class<ILiftingRuleBase> TYPE = ILiftingRuleBase.class;
	
	/**
	 * @return All recognition rules of this rulebase.
	 */
	public List<RecognitionRule> getRecognitionRules();
	
	/**
	 * Returns only the active recognition rules of this rulebase.
	 * 
	 * @return a list of recognition rules.
	 */
	public Set<Rule> getActiveRecognitonUnits();

	/**
	 * Returns the recognition rule (its a Henshin transformation "mainUnit") with the given name
	 * 
	 * @param name
	 *            of the recognition rule
	 * @return a recognition rule ({@link TransformationUnit})
	 */
	public Rule getRecognitionUnit(String name);

	/**
	 * Returns the corresponding recognition rule "mainUnit" for the given edit rule "mainUnit"
	 * 
	 * @param editRule
	 * @return the corresponding recognition rule ({@link TransformationUnit}).
	 */
	public Rule getRecognitionUnit(Unit editRule);
	
	/**
	 * Returns the corresponding edit rule "mainUnit" for the given recognition rule "mainUnit"
	 * 
	 * @param recognitionRule
	 * @return the corresponding edit rule ({@link TransformationUnit}).
	 */
	public Unit getEditUnit(Rule recognitionRule);
	
	/**
	 * Returns the trace from the given recognition rule node to the
	 * corresponding recognition rule node.
	 * 
	 * @param recognitionRuleNode
	 *            the recognition rule trace node.
	 * @return the corresponding {@link Trace} (Model A node of correspondence pattern)
	 */
	public Trace getTraceA(Node recognitionRuleNode);

	/**
	 * Returns the trace from the given recognition rule node to the corresponding recognition rule node.
	 * 
	 * @param recognitionRuleNode
	 *            the recognition rule trace node.
	 * @return the corresponding {@link Trace} (Model B node of correspondence pattern)
	 */
	public Trace getTraceB(Node recognitionRuleNode);
	
	/**
	 * Removes all cached values.
	 */
	public void clearCaches();
}
