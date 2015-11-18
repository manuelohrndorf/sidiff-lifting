package sample.metamodel.refined.matcher;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.matcher.BaseMatcher;

import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.Item_Link;
import sample.mm.refined.samplemm.SampleMetamodel;
import sample.mm.refined.samplemm.SamplemmPackage;

/**
 * Basically NamedElement Matcher, special handling of Root Element and
 * predecessor/successor Links.
 * 
 * @author kehrer
 */
public class SampleMMRefinedMatcher extends BaseMatcher {

	public static final String KEY = "Simplewebmodel";

	/**
	 * Initialize matcher and start matching.
	 */
	public SampleMMRefinedMatcher() {
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

		// "Singleton" elements
		if (elementA instanceof SampleMetamodel) {
			return true;
		}

		// Links
		if (elementA instanceof Item_Link) {
			Item preA = ((Item_Link) elementA).getPre();
			Item succA = ((Item_Link) elementA).getSucc();

			Item preB = ((Item_Link) elementB).getPre();
			Item succB = ((Item_Link) elementB).getSucc();

			return equalNames(preA, preB) && equalNames(succA, succB);
		}

		// Named Elements
		return equalNames(elementA, elementB);
	}

	private boolean equalNames(EObject elementA, EObject elementB) {
		if (elementA == null || elementB == null) {
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
		return "Sample MM Refined Matcher";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		String docTypeA = EMFModelAccess.getCharacteristicDocumentType(modelA);
		String docTypeB = EMFModelAccess.getCharacteristicDocumentType(modelB);

		return docTypeA.equals(SamplemmPackage.eNS_URI) && docTypeB.equals(SamplemmPackage.eNS_URI);
	}

	@Override
	public boolean canComputeReliability() {		
		return false;
	}
}
