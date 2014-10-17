package org.sidiff.difference.symmetric.compareview.ide.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class SiLiftToEMFCompareConverter {
	
	Comparison emfcompareDiff = CompareFactory.eINSTANCE.createComparison();
	
	List<Match> matches = new ArrayList<Match>();
	
	Map<EObject, Match> leftMatches = new HashMap<EObject, Match>();
	Map<EObject, Match> rightMatches = new HashMap<EObject, Match>();
	
	public SiLiftToEMFCompareConverter(SymmetricDifference siliftDiff) {
		
		// Convert difference:
		for(Correspondence correspondence : siliftDiff.getCorrespondences()) {
			try {
				EObject emfcompareDiffElement = convertSiLiftToEMFCompare(correspondence);
				SiLiftToEMFCompareProxy.replaceWithProxy(correspondence, emfcompareDiffElement);
			} catch (ConverterException e) {
				e.printStackTrace();
			}
		}
		
		for(Change change : siliftDiff.getChanges()) {
			try {
				EObject emfcompareDiffElement = convertSiLiftToEMFCompare(change);
				SiLiftToEMFCompareProxy.replaceWithProxy(change, emfcompareDiffElement);
			} catch (ConverterException e) {
//				e.printStackTrace();
			}
		}
		
		createEMFCompareStructure();
	}
	
	public EObject convertSiLiftToEMFCompare(EObject siliftDiff) throws ConverterException {
		
		/*
		 * TODO: Convert to the EMF-Compare difference model:
		 */
		
		if (siliftDiff instanceof Correspondence) {
			
			// Correspondence -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			Correspondence correspondence = (Correspondence) siliftDiff;

			match.setLeft(correspondence.getObjA());
			match.setRight(correspondence.getObjB());
			
			matches.add(match);
			leftMatches.put(correspondence.getObjA(), match);
			rightMatches.put(correspondence.getObjB(), match);
			
			return match;
		}
		
		else if (siliftDiff instanceof RemoveObject) {
			
			// RemoveObject -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			RemoveObject removeObject = (RemoveObject) siliftDiff;
			
			match.setLeft(removeObject.getObj());
			
			matches.add(match);
			leftMatches.put(removeObject.getObj(), match);
			
			return match;
		} 
		
		else if (siliftDiff instanceof AddObject) {
			
			// AddObject -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			AddObject addObject = (AddObject) siliftDiff;

			match.setRight(addObject.getObj());
			
			matches.add(match);
			rightMatches.put(addObject.getObj(), match);
			
			return match;
		}
		
//		else if (siliftDiff instanceof AddReference) {
//		
//			// AddReference -> ReferenceChange
//			ReferenceChange referenceChange = CompareFactory.eINSTANCE.createReferenceChange();
//			AddReference addReference = (AddReference) siliftDiff;
//		
//			referenceChange.setReference(addReference.getType());
//			referenceChange.setKind(DifferenceKind.ADD);
//			referenceChange.setSource(DifferenceSource.LEFT);
//		}
		
		else {
			throw new ConverterException();
		}
	}
	
	public void createEMFCompareStructure() {
		
		for(Match match : matches) {
			
			EObject left = match.getLeft();
			EObject right = match.getRight();
			
			if (left != null) {
				Match parentLeftMatch = leftMatches.get(left.eContainer());
				
				if (parentLeftMatch != null) {
					if (!parentLeftMatch.getSubmatches().contains(match)) {
						parentLeftMatch.getSubmatches().add(match);
					}
				} else {
					if (!emfcompareDiff.getMatches().contains(match)) {
						emfcompareDiff.getMatches().add(match);
					}
				}
			}
			
			if (right != null) {
				Match parentRightMatch = rightMatches.get(right.eContainer());
				
				if (parentRightMatch != null) {
					if (!parentRightMatch.getSubmatches().contains(match)) {
						parentRightMatch.getSubmatches().add(match);
					}
				} else {
					if (!emfcompareDiff.getMatches().contains(match)) {
						emfcompareDiff.getMatches().add(match);
					}
				}
			}
		}
	}

	public Comparison getEmfcompareDiff() {
		return emfcompareDiff;
	}
}
