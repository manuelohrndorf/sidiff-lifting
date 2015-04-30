package org.sidiff.mutation.selection.context;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.common.emf.annotation.AnnotateableElement;
import org.sidiff.mutation.selection.SimilaritySelection;

public class SimilarityContextSelection extends SimilaritySelection<Match> {

	public SimilarityContextSelection(LinkedList<Match> rankedCandidates,
			List<Match> selectedCandidates, int selectionCoveragePercent, int selectionMinimumNumber,int selectionMaxmimumNumber,
			boolean allowDuplicateCandidateSelection, boolean invertSort, int similarityRadius) {
		super(rankedCandidates, selectedCandidates, selectionCoveragePercent, selectionMinimumNumber, selectionMaxmimumNumber, 
				allowDuplicateCandidateSelection, invertSort, similarityRadius);
	}

	@Override
	protected void setFitness(Match candidate) {	
		
		Set<EObject> referencedEObjects = new HashSet<EObject>(candidate.getNodeTargets());

		for(EObject relevantEObject : getRelevantEObjects(referencedEObjects, this.getSimilarityRadius())){
			
			int candidateSelectedBefore = 0;
			
			// Check for Annotation on relevant EObjects
			AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(relevantEObject , AnnotateableElement.class);
			if(annotElem.hasAnnotation(SimilaritySelection.KEY_SELECTED)){
				candidateSelectedBefore += annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);
			}	
			
			// Add to new value
			++candidateSelectedBefore;
			
			// Annotate value
			annotElem.setAnnotation(SimilaritySelection.KEY_SELECTED, candidateSelectedBefore);

		}	
		
	}

	@Override
	protected int getFitness(Match candidate) {		
		
		int candidateSelectedBefore = 0;
		
		Set<EObject> referencedEObjects = new HashSet<EObject>(candidate.getNodeTargets());

		for(EObject relevantEObject : getRelevantEObjects(referencedEObjects,this.getSimilarityRadius())){
			// Check for Annotation on relevant EObjects
			AnnotateableElement annotElem = EMFAdapter.INSTANCE.adapt(relevantEObject , AnnotateableElement.class);
			if(annotElem.hasAnnotation(SimilaritySelection.KEY_SELECTED)){
				candidateSelectedBefore += annotElem.getAnnotation(SimilaritySelection.KEY_SELECTED, int.class);
			}				
		}
		
		//Invert as the fitness is defined inverse
		int fitness = candidateSelectedBefore * -1 ;
	
		return fitness;
	}
	
	/**
	 * Get all relevant Objects according to the given input objects as well as radius.
	 * @param inputObjects
	 * @param maximumRadius
	 * @return
	 */
	private Set<EObject> getRelevantEObjects(Set<EObject> inputObjects, int maximumRadius){
		
		Set<EObject> relevantEObjects = new HashSet<EObject>();		

		//The input objects themselves are relevant
		relevantEObjects.addAll(inputObjects);
		
		if(maximumRadius > 0){
			// Additional relevant EObjects are defined via the {@link SimilaritySelection.similarityRadius}
			relevantEObjects.addAll(getRelevantEObjects(relevantEObjects, maximumRadius-1));			
		}	
		
		return relevantEObjects;
	}
	
	
	
	
	
	

}
