package org.sidiff.difference.mutation.selection;

import java.util.LinkedList;


public class IntactSelection<T> extends AbstractSelection<T> {	

	public IntactSelection(LinkedList<T> rankedCandidates,
			LinkedList<T> selectedCandidates, int selectionCoveragePercent,
			boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent,
				allowDuplicateCandidateSelection);
	}

	@Override
	protected void updateCandidate(T candidate) {
		// Nothing to do		
	}

	@Override
	public int compare(T o1, T o2) {
		// Always equal, this leads to identity on list order
		return 0;
	}
	
	

}
