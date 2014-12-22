package org.sidiff.difference.matcher.uri;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.difference.matcher.BaseMatcher;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

/**
 * Matches two EObjects based on their EMF URI fragments, i.e. their "location"
 * within an EMF Resource.
 * 
 * Note that this URIFragmentMatcher is ready for profiled UML models.
 * 
 * @author kehrer
 */
public class URIFragmentMatcher extends BaseMatcher {

	public static final String KEY = "URIFragmentMatcher";

	private Map<EClass, Set<EReference>> stereotype2BaseReference;

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope,
			boolean calculateReliability) {
		stereotype2BaseReference = new HashMap<EClass, Set<EReference>>();
		super.createMatching(modelA, modelB, scope, calculateReliability);

		// Add all stereotypes to matching/difference
		matchStereotypes();

		// URI-based matching => reliability of matches = 1.0
		for (Correspondence c : getDifference().getCorrespondences()) {
			c.setReliability(1.0f);
		}

		return getDifference();
	}

	@Override
	protected boolean isCorresponding(EObject elementA, EObject elementB) {
		assert (elementA != null && elementB != null) : "One of the elements to check for correspondence is null!";

		// type should be identical in order to correspond
		if (elementA.eClass() != elementB.eClass()) {
			return false;
		}

		// don't match Stereotype objects because URI might be misleading
		if (isStereotype(elementA.eClass())) {
			return false;
		}

		// None of the elements must be already in a correspondence
		if (hasCorrespondence(elementA) || hasCorrespondence(elementB)) {
			return false;
		}

		// Retrieve URIFragments and compare
		String fragmentA = EcoreUtil.getURI(elementA).fragment();
		String fragmentB = EcoreUtil.getURI(elementB).fragment();

		if (fragmentA != null && fragmentB != null) {
			return fragmentA.equals(fragmentB);
		}

		return false;
	}

	private boolean isStereotype(EClass type) {
		List<EStructuralFeature> allBaseReferences = EMFMetaAccess.getEStructuralFeaturesByRegEx(type, "^(base)_\\w+",
				true);

		if (allBaseReferences.size() > 0) {
			// cache stereotype and baseReference(s)
			for (EStructuralFeature eStructuralFeature : allBaseReferences) {
				putStereotype2BaseReference(type, (EReference) eStructuralFeature);
			}

			return true;
		}

		return false;
	}

	// TODO(DR): Maybe we can find a solution to reuse profile matching
	// functionality of SiDiff ProfileMatchingService!
	// This is just the semantics of this service copied over here, bad smell!
	private void matchStereotypes() {
		// Create maps for used profile elements
		// baseObj -> Set(stereoObjs)
		Map<EObject, Set<EObject>> profileMapA = new HashMap<EObject, Set<EObject>>();
		Map<EObject, Set<EObject>> profileMapB = new HashMap<EObject, Set<EObject>>();

		/*
		 * 
		 * Build maps between baseTypes and stereotypes for both resources
		 * Should be faster in long term runs if this map is used instead of
		 * iterating over all objects again Map is constructed like : StereoType
		 * -> BaseType
		 */

		Iterator<EObject> itA = getModelA().getAllContents();
		while (itA.hasNext()) {

			// Iterate through all elements of resource
			EObject obj = itA.next();

			for (EClass stereoType : stereotype2BaseReference.keySet()) {

				// If current element is a stereotype
				if (obj.eClass().equals(stereoType)) {
					// Get base object
					// Note: es kann mehrere baseReferenzTypen geben, aber nur
					// eine baseReferenz
					EObject baseObj = null;
					for (EReference ref : stereotype2BaseReference.get(stereoType)) {
						if (obj.eGet(ref) != null) {
							baseObj = (EObject) obj.eGet(ref);
						}
					}

					assert (baseObj != null) : "Stereotype without base element..!?";
					putBaseObject2StereoObject(profileMapA, baseObj, obj);
				}
			}
		}

		Iterator<EObject> itB = getModelB().getAllContents();
		while (itB.hasNext()) {

			// Iterate through all elements of resource
			EObject obj = itB.next();

			for (EClass stereoType : stereotype2BaseReference.keySet()) {

				// If current element is a stereotype
				if (obj.eClass().equals(stereoType)) {
					// Get base object
					// Note: es kann mehrere baseReferenzTypen geben, aber nur
					// eine baseReferenz
					EObject baseObj = null;
					for (EReference ref : stereotype2BaseReference.get(stereoType)) {
						if (obj.eGet(ref) != null) {
							baseObj = (EObject) obj.eGet(ref);
						}
					}

					assert (baseObj != null) : "Stereotype without base element..!?";
					putBaseObject2StereoObject(profileMapB, baseObj, obj);
				}
			}
		}

		/*
		 * Create correspondences / matchings between stereotype elements The
		 * created correspondences are based on the correspondences between the
		 * corresponding basetype elements
		 */

		// Iterate over all (stereoObj,baseObjs) pairs in A
		for (EObject baseObjA : profileMapA.keySet()) {
			for (EObject stereoObjA : profileMapA.get(baseObjA)) {
				// Get corresponding baseObj in B
				if (getDifference().getCorrespondingObjectInB(baseObjA) != null) {
					EObject baseObjB = getDifference().getCorrespondingObjectInB(baseObjA);

					// Now try to get the right stereoObjB
					for (EObject stereoObjB : profileMapB.get(baseObjB)) {
						if (stereoObjB.eClass() == stereoObjA.eClass()) {
							// Add correspondence between stereotypes
							getDifference().addCorrespondence(stereoObjA, stereoObjB);
						}
					}
				}
			}
		}
	}

	private void putBaseObject2StereoObject(Map<EObject, Set<EObject>> map, EObject baseObj, EObject stereoObj) {
		Set<EObject> stereoObjs = map.get(baseObj);
		if (stereoObjs == null) {
			stereoObjs = new HashSet<EObject>();
			map.put(baseObj, stereoObjs);
		}

		stereoObjs.add(stereoObj);
	}

	private void putStereotype2BaseReference(EClass stereoType, EReference baseRef) {
		Set<EReference> refs = stereotype2BaseReference.get(stereoType);
		if (refs == null) {
			refs = new HashSet<EReference>();
			stereotype2BaseReference.put(stereoType, refs);
		}

		if (!refs.contains(baseRef)) {
			refs.add(baseRef);
		}
	}

	@Override
	public String getName() {
		return "URIFragment Matcher (Signature)";
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getDocumentType() {
		return EMFModelAccessEx.GENERIC_DOCUMENT_TYPE;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}

}
