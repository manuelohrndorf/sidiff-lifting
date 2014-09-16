package org.sidiff.difference.matcher.ecoreid;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.matcher.BaseMatcher;
import org.silift.common.util.access.EMFModelAccessEx;

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
	public String getDocumentType() {
		// can handle every documentType (maybe with bad results when only few
		// id attributes are defined and correctly used)
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
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
		if (idAttrA == null || idAttrB == null) {
			return false;
		} else {
			Object idA = elementA.eGet(idAttrA);
			Object idB = elementB.eGet(idAttrB);

			if (idA == null || idB == null) {
				return false;
			} else {
				return idA.equals(idB);
			}
		}
	}
}
