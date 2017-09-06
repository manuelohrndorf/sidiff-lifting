package org.sidiff.difference.asymmetric.dependencies.real;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.symmetric.SemanticChangeSet;

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
		this.recognitionEngine = recognitionEngine;
	}

	@Override
	protected void initialize() {
		this.ruleBases = recognitionEngine.getSetup().getRulebases();
		this.editRule2SCS = recognitionEngine.getChangeSets();
	}

	@Override
	protected IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		 return recognitionEngine.getEditRuleMatch(scs);
	}

	
}
