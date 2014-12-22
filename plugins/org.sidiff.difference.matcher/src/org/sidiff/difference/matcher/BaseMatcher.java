package org.sidiff.difference.matcher;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;

/**
 * Search for corresponding objects from model A to model B. Clients may
 * subclass this basic matcher and provide a specific implementation for method
 * {@link BaseMatcher#isCorresponding(EObject, EObject)}
 */
public abstract class BaseMatcher extends AbstractMatcher {

	@Override
	protected void compareResources(){
		if (scope == Scope.RESOURCE_SET) {
			// Include all ResourceSets A for matching
			for (Resource r : modelA.getResourceSet().getResources()) {
				traverseResourceA(r);
			}
		} else {
			// Consider only resource model A
			traverseResourceA(modelA);
		}
	}
	
	private void traverseResourceA(Resource resourceA) {
		EObject elementA = null;
		TreeIterator<EObject> iterA = resourceA.getAllContents();

		while (iterA.hasNext()) {
			elementA = iterA.next();

			if (scope == Scope.RESOURCE_SET) {
				// Include all resource sets B for search
				for (Resource r : modelB.getResourceSet().getResources()) {
					EObject elementB = null;
					TreeIterator<EObject> iterB = r.getAllContents();

					boolean matchFound = false;
					while (iterB.hasNext()) {
						elementB = iterB.next();

						if (doProcess(elementA) && doProcess(elementB)) {
							if (isCorresponding(elementA, elementB)) {
								matching.addCorrespondence(elementA, elementB);
								matchFound = true;
								break;
							}
						}
					}

					if (matchFound) {
						break;
					}
				}
			} else {
				// Consider only resource model B for search
				EObject elementB = null;
				TreeIterator<EObject> iterB = modelB.getAllContents();

				while (iterB.hasNext()) {
					elementB = iterB.next();

					if (doProcess(elementA) && doProcess(elementB)) {
						if (isCorresponding(elementA, elementB)) {
							matching.addCorrespondence(elementA, elementB);
							break;
						}
					}
				}
			}
		}
	}

	private boolean doProcess(EObject object) {
		// generic matchers can process every eObject
		if (getDocumentType().equals(EMFModelAccessEx.GENERIC_DOCUMENT_TYPE)) {
			return true;
		}

		return EMFModelAccessEx.getDocumentType(object).equals(getDocumentType());
	}

	/**
	 * Calculates matching between to objects. Subclasses have to implement this
	 * method!
	 * 
	 * @param elementA
	 *            the model A version of the object.
	 * @param elementB
	 *            the model B version of the object.
	 * @return true if the objects are corresponding to each other; false
	 *         otherwise.
	 */
	protected abstract boolean isCorresponding(EObject elementA, EObject elementB);


}
