package org.sidiff.difference.lifting.recognitionengine;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;

/**
 * The recognition engine is used to execute the recognition rules on the
 * difference. The recognition rules will group the atomic changes to semantic
 * change sets. Note that there maybe overlapping semantic change sets.
 */
public interface IRecognitionEngine {

	/**
	 * @return The recognition engine setup.
	 */
	public RecognitionEngineSetup getSetup();

	/**
	 * Execution of all recognition rules.
	 */
	public void execute();
	
	/**
	 * @return All applied recognition rule applications.
	 */
	public Set<RuleApplication> getRecognitionRuleApplications();

	/**
	 * @return Mapping: edit rule (r) -> set of semantic change sets
	 *         representing an invocation of (r)
	 */
	public Map<EditRule, Set<SemanticChangeSet>> getChangeSets();

	/**
	 * Get "trace" back to edit rule for given semantic change sets.
	 * 
	 * @param scs
	 *            The corresponding semantic change sets.
	 * @return
	 */
	public IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs);

	/**
	 * Get "trace" back to recognition rule for given semantic change sets.
	 * 
	 * @param scs
	 *            The corresponding semantic change sets.
	 * @return An object which allows access to the matching of the recognition rule.
	 */
	public IRecognitionRuleMatch getRecognitionRuleMatch(SemanticChangeSet changeSet);

	/**
	 * Removes a a recognition rule match and a edit rule match for a given
	 * semantic change sets.
	 * 
	 * @param scs
	 *            The corresponding semantic change sets.
	 */
	public void removeMatches(SemanticChangeSet nextCS);
	
	/**
	 * @return A tool which can be used to test the performance of the
	 *         recognition engine
	 */
	public IRecognitionEngineStatistics getStatistic();
}
