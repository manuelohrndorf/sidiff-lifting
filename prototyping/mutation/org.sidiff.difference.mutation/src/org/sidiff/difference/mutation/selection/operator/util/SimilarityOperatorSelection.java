package org.sidiff.difference.mutation.selection.operator.util;

import java.util.LinkedList;

import org.sidiff.difference.mutation.selection.SimilaritySelection;
import org.sidiff.difference.rulebase.EditRule;

public class SimilarityOperatorSelection extends SimilaritySelection<EditRule> {

	public SimilarityOperatorSelection(LinkedList<EditRule> rankedCandidates,
			LinkedList<EditRule> selectedCandidates,
			int selectionCoveragePercent,
			boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent,
				allowDuplicateCandidateSelection);
	}

	@Override
	protected void setFitness(EditRule candidate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getFitness(EditRule candidate) {
		// TODO Auto-generated method stub
		return 0;
	}
}
