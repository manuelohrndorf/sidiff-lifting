package org.sidiff.difference.symmetric.compareview.ide.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class SiLiftToEMFCompareConverter {

	// TODO set "required"
	Comparison emfcompareDiff = CompareFactory.eINSTANCE.createComparison();
	
	// TODO: speicher optimieren
	List<Match> matches = new ArrayList<Match>();	
	Map<EObject, MatchTrace> leftMatches = new HashMap<EObject, MatchTrace>();
	Map<EObject, MatchTrace> rightMatches = new HashMap<EObject, MatchTrace>();
	
	public SiLiftToEMFCompareConverter(SymmetricDifference siliftDiff) {
		
		// Convert SiLift difference to EMF-Compare difference:
		createMatchModel(siliftDiff);
		createMatchStructure();
		createChanges(siliftDiff);
	}
	
	private class MatchTrace {
		EObject siliftDiffElement;	// Correspondence / AddObject / RemoveObject
		Match emfcompareMatch;

		public MatchTrace(EObject siliftDiffElement, Match emfcompareMatch) {
			super();
			this.siliftDiffElement = siliftDiffElement;
			this.emfcompareMatch = emfcompareMatch;
		}
	}

	private void createMatchModel(SymmetricDifference siliftDiff) {
		
		for(Correspondence correspondence : siliftDiff.getCorrespondences()) {
			Match match = convertCorrespondence(correspondence);
			SiLiftToEMFCompareProxy.replaceWithProxy(correspondence, match);
		}
		
		for(Change change : siliftDiff.getChanges()) {
			if (change instanceof AddObject) {
				convertAddObject((AddObject) change);
				// Add-Object Connector => Use the EMF-Compare containment reference change instead of the match.
				// => EMF-Compare uses the (containment) change reference to visualize added Objects. 
			}
			
			else if (change instanceof RemoveObject) {
				convertRemoveObject((RemoveObject) change);
				// Remove-Object Connector => Use the EMF-Compare containment reference change instead of the match.
				// => EMF-Compare uses the (containment) change reference to visualize remove Objects. 
			}
		}
	}
	
	private Match convertCorrespondence(Correspondence correspondence) {

		// Convert to the EMF-Compare match model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD

		// Correspondence -> Match: 
		Match match = CompareFactory.eINSTANCE.createMatch();
		
		match.setRight(correspondence.getObjA());
		match.setLeft(correspondence.getObjB());
		
		matches.add(match);
		
		MatchTrace matchTrace = new MatchTrace(correspondence, match);
		rightMatches.put(correspondence.getObjA(), matchTrace);
		leftMatches.put(correspondence.getObjB(), matchTrace);
		
		return match;
	}
	
	private Match convertRemoveObject(RemoveObject removeObject) {
		
		// Convert to the EMF-Compare match model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD

		// RemoveObject -> Match: 
		Match match = CompareFactory.eINSTANCE.createMatch();
		match.setRight(removeObject.getObj());
		
		matches.add(match);
		rightMatches.put(removeObject.getObj(), new MatchTrace(removeObject, match));
		
		return match;
	}
	
	private Match convertAddObject(AddObject addObject) {
		
		// Convert to the EMF-Compare match model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD
		
		// AddObject -> Match: 
		Match match = CompareFactory.eINSTANCE.createMatch();
		match.setLeft(addObject.getObj());
		
		matches.add(match);
		leftMatches.put(addObject.getObj(), new MatchTrace(addObject, match));
		
		return match;
	}
	
	private void createMatchStructure() {
		
		for(Match match : matches) {
			
			EObject left = match.getLeft();
			EObject right = match.getRight();
			
			if (left != null) {
				MatchTrace parentLeftMatch = leftMatches.get(left.eContainer());
				
				if (parentLeftMatch != null) {
					if (!parentLeftMatch.emfcompareMatch.getSubmatches().contains(match)) {
						parentLeftMatch.emfcompareMatch.getSubmatches().add(match);
						continue;
					}
				} else {
					if (!emfcompareDiff.getMatches().contains(match)) {
						emfcompareDiff.getMatches().add(match);
						continue;
					}
				}
			}
			
			if (right != null) {
				MatchTrace parentRightMatch = rightMatches.get(right.eContainer());
				
				if (parentRightMatch != null) {
					if (!parentRightMatch.emfcompareMatch.getSubmatches().contains(match)) {
						parentRightMatch.emfcompareMatch.getSubmatches().add(match);
					}
				} else {
					if (!emfcompareDiff.getMatches().contains(match)) {
						emfcompareDiff.getMatches().add(match);
					}
				}
			}
		}
	}
	
	private void createChanges(SymmetricDifference siliftDiff) {
		
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD
		
		for(Change siliftChange : siliftDiff.getChanges()) {

			if (siliftChange instanceof AddReference) {
				AddReference addReference = (AddReference) siliftChange;
				
				ReferenceChange emfChangeReference = convertAddReference(addReference);
				SiLiftToEMFCompareProxy.replaceWithProxy(siliftChange, emfChangeReference);
				
				// Add-Object Connector => Use the EMF-Compare containment reference change instead of the match.
				// => EMF-Compare uses the (containment) change reference to visualize added Objects. 
				if (addReference.getType().isContainment()) {
					AddObject addObject = (AddObject) leftMatches.get(addReference.getTgt()).siliftDiffElement;
					SiLiftToEMFCompareProxy.replaceWithProxy(addObject, emfChangeReference);
				}
			}
			
			else if (siliftChange instanceof RemoveReference) {
				RemoveReference removeReference = (RemoveReference) siliftChange;
				
				ReferenceChange emfChangeReference = convertRemoveReference((RemoveReference) siliftChange);
				SiLiftToEMFCompareProxy.replaceWithProxy(siliftChange, emfChangeReference);
				
				// Remove-Object Connector => Use the EMF-Compare containment reference change instead of the match.
				// => EMF-Compare uses the (containment) change reference to visualize removed Objects. 
				if (removeReference.getType().isContainment()) {
					RemoveObject removeObject = (RemoveObject) rightMatches.get(removeReference.getTgt()).siliftDiffElement;
					SiLiftToEMFCompareProxy.replaceWithProxy(removeObject, emfChangeReference);
				}
			}
			
			else if (siliftChange instanceof AttributeValueChange) {
				AttributeChange emfcompareDiffElement = convertAttributeValueChange((AttributeValueChange) siliftChange);
				SiLiftToEMFCompareProxy.replaceWithProxy(siliftChange, emfcompareDiffElement);
			}
		}
	}

	private ReferenceChange convertAddReference(AddReference addReference) {
		
		// Convert to the EMF-Compare difference model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD

		// AddReference -> ReferenceChange
		ReferenceChange referenceChange = CompareFactory.eINSTANCE.createReferenceChange();
		
		Match match = leftMatches.get(addReference.getSrc()).emfcompareMatch;
		match.getDifferences().add(referenceChange);
		
		referenceChange.setKind(DifferenceKind.ADD);
		referenceChange.setSource(DifferenceSource.LEFT);		// LEFT => CHANGED
		referenceChange.setState(DifferenceState.UNRESOLVED);
		
		referenceChange.setMatch(match); 						// SRC
		referenceChange.setValue(addReference.getTgt()); 		// TGT
		referenceChange.setReference(addReference.getType()); 	// TYPE
		
		return referenceChange;
	}
	
	private ReferenceChange convertRemoveReference(RemoveReference removeReference) {
		
		// Convert to the EMF-Compare difference model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD

		// RemoveReference -> ReferenceChange
		ReferenceChange referenceChange = CompareFactory.eINSTANCE.createReferenceChange();
		
		Match match = rightMatches.get(removeReference.getSrc()).emfcompareMatch;
		match.getDifferences().add(referenceChange);
		
		referenceChange.setKind(DifferenceKind.DELETE);
		referenceChange.setSource(DifferenceSource.LEFT);			// LEFT => CHANGED
		referenceChange.setState(DifferenceState.UNRESOLVED);
		
		referenceChange.setMatch(match); 							// SRC
		referenceChange.setValue(removeReference.getTgt()); 		// TGT
		referenceChange.setReference(removeReference.getType()); 	// TYPE
		
		return referenceChange;
	}
	
	private AttributeChange convertAttributeValueChange(AttributeValueChange attributeValueChange) {
		
		// Convert to the EMF-Compare difference model:
		// RIGHT === A === REMOVE
		// LEFT  === B === ADD

		// AttributeValueChange -> AttributeChange
		AttributeChange attributeChange = CompareFactory.eINSTANCE.createAttributeChange();
		
		Match match = rightMatches.get(attributeValueChange.getObjA()).emfcompareMatch;
		match.getDifferences().add(attributeChange);
		
		assert (leftMatches.get(attributeValueChange.getObjB()).emfcompareMatch == match) : "Inconsistent matching!";
		
		attributeChange.setKind(DifferenceKind.CHANGE);			// (?) CHANGE_VALUE
		attributeChange.setSource(DifferenceSource.LEFT);		// LEFT => CHANGED
		attributeChange.setState(DifferenceState.UNRESOLVED);
		
		attributeChange.setAttribute(attributeValueChange.getType());
		attributeChange.setMatch(match);
		
		return attributeChange;
	}

	public Comparison getEmfcompareDiff() {
		return emfcompareDiff;
	}
}
