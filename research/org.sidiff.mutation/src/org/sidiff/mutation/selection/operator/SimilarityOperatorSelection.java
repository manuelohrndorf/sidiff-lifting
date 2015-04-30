package org.sidiff.mutation.selection.operator;

import java.util.LinkedList;
import java.util.List;

import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.mutation.selection.SimilaritySelection;

public class SimilarityOperatorSelection extends SimilaritySelection<EditRule> {

	public SimilarityOperatorSelection(LinkedList<EditRule> rankedCandidates,
			List<EditRule> selectedCandidates,
			int selectionCoveragePercent, int selectionMinimumNumber, int selectionMaxmimumNumber,
			boolean allowDuplicateCandidateSelection, boolean invertSort, int similarityRadius) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent, selectionMinimumNumber, selectionMaxmimumNumber,
				allowDuplicateCandidateSelection, invertSort, similarityRadius);
	}

	@Override
	protected void setFitness(EditRule candidate) {

		int candidateSelectedBefore = 0;

		// We annotate the operator
		AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(candidate , AnnotateableElement.class);
		
		// Get old value and add it
		if(annotElem.hasAnnotation(SimilaritySelection.KEY_SELECTED)){
			candidateSelectedBefore += annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);				
		}			
		// Annotate with new value
		++candidateSelectedBefore;
		annotElem.setAnnotation(SimilaritySelection.KEY_SELECTED, candidateSelectedBefore);
	}


	@Override
	protected int getFitness(EditRule candidate) {
		
		int candidateSelectedBefore = 0;

		// We get the current annotation value
		AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(candidate , AnnotateableElement.class);
		if(annotElem.hasAnnotation(SimilaritySelection.KEY_SELECTED)){
			candidateSelectedBefore = annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);				
		}		
		//Invert as the fitness is defined inverse
		int fitness = candidateSelectedBefore * -1 ;
		
		return fitness;
	}
}
