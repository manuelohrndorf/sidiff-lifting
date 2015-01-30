package org.sidiff.difference.mutation.selection;

import java.util.LinkedList;

public abstract class SimilaritySelection<T> extends AbstractSelection<T> {

	public SimilaritySelection(LinkedList<T> rankedCandidates,
			LinkedList<T> selectedCandidates, int selectionCoveragePercent,
			boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent,
				allowDuplicateCandidateSelection);
	}
	
	/**
	 * Annotation key for selection
	 */
	public static final String KEY_SELECTED = "Selected";
	
	/**
	 * Annotation key for selection
	 */
	public static final String KEY_MODIFIED = "Modified";
	
	/**
	 * Defines whether the similarity sorting is inverted.
	 * This leads to opposite results for the ranking of contexts.
	 */
	private boolean invertSorting;

	@Override
	protected void updateCandidate(T candidate) {
		setFitness(candidate);		
	}
	
	@Override
	public int compare(T o1, T o2) {

		int result;

		int o1Fitness = getFitness(o1);
		int o2Fitness = getFitness(o2);

		if(o1Fitness == o2Fitness)
			result = 0;
		else if(o1Fitness < o2Fitness)
			result = -1;
		else
			result = 1;
		
		if(invertSorting)
			result *= -1 ;

		return result;
	
	}
	
	protected abstract void setFitness(T candidate);

	protected abstract int getFitness(T candidate);
	
}
