package org.sidiff.difference.matcher.dcu;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.difference.matcher.BaseMatcher;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MGuard;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

/**
 * Concrete matcher stub for DCU. 
 * 
 * @author dreuling
 */
public class DCUMatcher extends BaseMatcher {

	public static final String KEY = "DCU";	

	private Map<String, Object> configuration;

	/**
	 * Initialize matcher and start matching.
	 */
	public DCUMatcher() {
		super();
		this.configuration = new HashMap<String, Object>();

	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";

		// type should be identical in order to correspond
		if (elementA.eClass() != elementB.eClass()) {
			return false;
		}

		// None of the elements must be already in a correspondence
		if (hasCorrespondence(elementA) || hasCorrespondence(elementB)) {
			return false;
		}
		// Statemachines are always corresponding, as there is only one
		if(elementA instanceof MStateMachine && elementB instanceof MStateMachine)
			return true;

		// Regions are always corresponding, as there is only one
		if(elementA instanceof MRegion && elementB instanceof MRegion)
			return true;
		
		
		// Transitions are matched using their signatures
		if(elementA instanceof MTransition && elementB instanceof MTransition){
			MAbstractState srcStateA = ((MTransition)elementA).getSourceState();
			MAbstractState srcStateB = ((MTransition)elementB).getSourceState();
			MAbstractState tgtStateA = ((MTransition)elementA).getTargetState();
			MAbstractState tgtStateB = ((MTransition)elementB).getTargetState();
			MGuard guardA = ((MTransition)elementA).getGuard();
			MGuard guardB = ((MTransition)elementB).getGuard();

			if(nameEquals(srcStateA, srcStateB) && nameEquals(tgtStateA, tgtStateB)
					&& nameEquals(guardA, guardB))
				return true;
			else
				return false;
		}
		
		//All remaining elements are matched using their name (for now)
		return nameEquals(elementA, elementB);
	}
		
		private boolean nameEquals(EObject elementA, EObject elementB){
		// Check for attribute "name" and its values equality
		EStructuralFeature attrName = elementA.eClass().getEStructuralFeature("name");
		if (attrName != null && attrName instanceof EAttribute) {
			Object nameA;
			Object nameB;


			nameA = elementA.eGet(attrName);
			nameB = elementB.eGet(attrName);

			if (nameA != null && nameB != null) {
				
				//First equality
				if(nameA.equals(nameB))
					return true;
				
				//Hamming distance between names
//				double similarity = similarity((String)nameA, (String)nameB);
//				if(similarity > 0.8)
//					return true;
			}
		}

		return false;
	}
		

	@Override
	public String getName() {
		return "DCU Matcher (Signature)";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		// can handle IMoTEP SMM
		return De_imotep_core_behaviorPackage.eNS_URI;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	public Map<String, Object> getConfigurationOptions() {
		return configuration;
	}
	
    /**
     * Calculate the similarity of two strings.
     *
     * @param a
     *            - the first string
     * @param b
     *            - the second string
     * @return the similarity
     */
    private double similarity(final String a, final String b) {
	// return 1.0 in case of identity (includes both being null)
	if (a == b)
	    return 1.0;

	// return null if one of the strings is null
	if (a == null || b == null)
	    return 0.0;

	// normalize value to [0,1] by dividing by length of the longer string,
	// then convert distance to similarity
	return 1.0 - hammingDistance(a, b) * 1.0 / Math.max(a.length(), b.length());
    }


    // calculates the Hamming-Distance of two strings
    private int hammingDistance(final String a, final String b) {
	final int la = a.length();
	final int lb = b.length();
	// treat missing characters as difference
	int d = Math.abs(la - lb);
	// count number of differences
	for (int i = 0; i < Math.min(la, lb); i++)
	    if (a.charAt(i) != b.charAt(i))
		++d;
	return d;
    }
	
}
