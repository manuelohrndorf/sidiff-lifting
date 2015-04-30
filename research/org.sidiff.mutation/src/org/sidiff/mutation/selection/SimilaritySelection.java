package org.sidiff.mutation.selection;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class SimilaritySelection<T> extends AbstractSelection<T> {
	
	/**
	 * Annotation key for selection
	 */
	public static final String KEY_SELECTED = "Selected";
	
	/**
	 * Defines whether the similarity sorting is inverted.
	 * This leads to opposite results for the ranking of contexts.
	 */
	private boolean invertSorting;
	
	
	/**
	 * Defines a radius of similarity. This is used to
	 * determine how "far" the similarity selection will
	 * reach in terms of elements. If radius is set to zero
	 * only the directly used elements are taken into consideration.
	 * Using this can lead to groups of elements based on their connection
	 * to each other.
	 */
	private int similarityRadius;
	

	public SimilaritySelection(LinkedList<T> rankedCandidates,
			List<T> selectedCandidates, int selectionCoveragePercent, int selectionMinimumNumber,int selectionMaxmimumNumber, boolean
			allowDuplicateCandidateSelection, boolean invertSorting, int similarityRadius) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent, selectionMinimumNumber,selectionMaxmimumNumber,
				allowDuplicateCandidateSelection);
		this.invertSorting = invertSorting;
		this.similarityRadius =  similarityRadius;
	}

	/**
	 * Randomize the candidates at the beginning
	 */
	@Override
	public void initializeCandidates(LinkedList<T> candidates) {
		Collections.shuffle(candidates);
		super.initializeCandidates(candidates);
	}

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
			result = 1;
		else
			result = -1;
		
		if(invertSorting)
			result *= -1 ;	
		
		return result;
	
	}
	
	public int getSimilarityRadius() {
		return similarityRadius;
	}
	
	protected abstract void setFitness(T candidate);

	protected abstract int getFitness(T candidate);

	

}
