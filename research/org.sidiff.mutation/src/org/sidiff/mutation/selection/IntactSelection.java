package org.sidiff.mutation.selection;

import java.util.LinkedList;
import java.util.List;


public class IntactSelection<T> extends AbstractSelection<T> {	

	public IntactSelection(LinkedList<T> rankedCandidates,
			List<T> selectedCandidates, int selectionCoveragePercent, int selectionMinimumNumber,int selectionMaxmimumNumber,
			boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent,
				selectionMinimumNumber, selectionMaxmimumNumber,allowDuplicateCandidateSelection);
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
