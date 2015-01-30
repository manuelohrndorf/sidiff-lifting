package org.sidiff.difference.mutation.selection.context;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.difference.mutation.selection.SimilaritySelection;

public class SimilarityContextSelection extends SimilaritySelection<Match> {

	public SimilarityContextSelection(LinkedList<Match> rankedCandidates,
			LinkedList<Match> selectedCandidates, int selectionCoveragePercent,
			boolean invertSort,	boolean allowDuplicateCandidateSelection) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent, invertSort,
				allowDuplicateCandidateSelection);
	}

	@Override
	protected void setFitness(Match candidate) {
		
		int candidateSelectedBefore = 0;
		
		// We annotate all referenced EObjects in
		// the current context.
		for(EObject refObj : candidate.getNodeTargets()){
			AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(refObj , AnnotateableElement.class);
		
			// Get old value and add it
			if(annotElem.hasAnnotation(SimilarityContextSelection.KEY_SELECTED)){
				candidateSelectedBefore += annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);				
			}			
			// Annotate with new value
			++candidateSelectedBefore;
			annotElem.setAnnotation(SimilaritySelection.KEY_SELECTED, candidateSelectedBefore);
		}
	}

	@Override
	protected int getFitness(Match candidate) {		
		
		int candidateSelectedBefore = 0;

		for(EObject objo1 : candidate.getNodeTargets()){
			// Check for Annotation on referenced EObjects
			AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(objo1 , AnnotateableElement.class);
			if(annotElem.hasAnnotation(SimilaritySelection.KEY_SELECTED)){
				candidateSelectedBefore += annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);
			}				
		}
		
		//Invert as the fitness is defined inverse
		int fitness = candidateSelectedBefore * -1 ;
		
		return fitness;
	}

}
