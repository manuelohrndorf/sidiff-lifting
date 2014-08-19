package org.sidiff.difference.matcher.ecoreid;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.BaseMatcher;

public class EcoreIDMatcher extends BaseMatcher {
	
	public static final String KEY = "EcoreIDMatcher";


	@Override
	public String getName() {
		return "EcoreID Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		
		// can handle every documentType (maybe with bad results when only few
		// id attributes are defined and correctly used)
		return true;

	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";		

		// None of the elements must be already in a correspondence
		if (isCorresponding(elementA) || isCorresponding(elementB)) {
			return false;
		}

		// Check for ID attribute
		EAttribute idAttrA = elementA.eClass().getEIDAttribute();
		EAttribute idAttrB = elementB.eClass().getEIDAttribute();
		if(idAttrA == null || idAttrB == null){
			return false;
		}
		else{
			Object idA = elementA.eGet(idAttrA);
			Object idB = elementB.eGet(idAttrB);

			if(idA == null || idB == null){
				return false;
			}
			else{
				return idA.equals(idB);
			}
		}
	}
}
