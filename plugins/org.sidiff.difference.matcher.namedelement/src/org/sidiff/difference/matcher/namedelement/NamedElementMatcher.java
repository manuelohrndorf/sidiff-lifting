package org.sidiff.difference.matcher.namedelement;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.matcher.BaseMatcher;
import org.silift.common.util.access.EMFModelAccessEx;

/**
 * Concrete matcher stub that checks whether two elements have a name attribute
 * and, if so, checks if the names are equal Strings.
 * 
 * @author kehrer
 */
public class NamedElementMatcher extends BaseMatcher {

	public static final String KEY = "NamedElement";

	/**
	 * Initialize named element matcher and start matching.
	 */
	public NamedElementMatcher() {
		super();
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";

		// type should be identical in order to correspond
		if (elementA.eClass() != elementB.eClass()) {
			return false;
		}

		// None of the elements must be already in a correspondence
		if (isCorresponding(elementA) || isCorresponding(elementB)) {
			return false;
		}

		// Check for attribute "name" and its values equality
		EStructuralFeature attrName = elementA.eClass().getEStructuralFeature("name");
		if (attrName != null && attrName instanceof EAttribute) {
			Object nameA = elementA.eGet(attrName);
			Object nameB = elementB.eGet(attrName);

			if (nameA != null && nameB != null) {
				return nameA.equals(nameB);
			}
		}

		return false;
	}

	@Override
	public String getName() {
		return "NamedElement Matcher (Signature)";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		// can handle every documentType (maybe with bad results when only few
		// name attributes are defined in the meta-model)
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
}
