package org.sidiff.difference.mutation.selection;

import java.util.LinkedList;
import java.util.Random;

public class RandomSelection<T> extends AbstractSelection<T> {

	public RandomSelection(LinkedList<T> rankedCandidates,
			LinkedList<T> selectedCandidates, int selectionCoveragePercent,
			boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent,
				allowDuplicateCandidateSelection);
	}

	@Override
	protected T getNextCandidate() {		
		//Just return a random operator
		Random rand = new Random();
		int index = rand.nextInt(getRankedCandidates().size());
		return getRankedCandidates().get(index);
	}
	
	@Override
	protected void updateCandidate(T candidate) {
		// Nothing to do
	}

	@Override
	public int compare(T o1, T o2) {
		// All candidates are equal as they
		// are chosen randomly.
		return 0;
	}
}
