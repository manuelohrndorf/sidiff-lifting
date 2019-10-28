package org.sidiff.difference.asymmetric.dependencies.real;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;

public class EngineBasedDependencyAnalyzer extends DependencyAnalyzer {

	/**
	 * The RecognitionEngine instance that was used to semantically lift a
	 * difference
	 */
	private IRecognitionEngine recognitionEngine;

	/**
	 * Creates a {@link EngineBasedDependencyAnalyzer}
	 * 
	 * @param recognitionEngine
	 *            The RecognitionEngine instance that was used to semantically
	 *            lift a difference
	 */
	public EngineBasedDependencyAnalyzer(AsymmetricDifference asymmetricDiff, IRecognitionEngine recognitionEngine) {
		super(asymmetricDiff);
		this.recognitionEngine = Objects.requireNonNull(recognitionEngine, "recognitionEngine is null");
	}

	@Override
	protected Set<ILiftingRuleBase> initializeRuleBases() {
		return recognitionEngine.getSetup().getRulebases();
	}

	@Override
	protected Map<EditRule, Set<SemanticChangeSet>> initializeEditRule2SCS() {
		return recognitionEngine.getChangeSets();
	}

	@Override
	protected IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		 return recognitionEngine.getEditRuleMatch(scs);
	}
}
