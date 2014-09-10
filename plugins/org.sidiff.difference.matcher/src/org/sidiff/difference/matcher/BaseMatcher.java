package org.sidiff.difference.matcher;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.emf.Scope;

/**
 * Search for corresponding objects from model A to model B. Clients may
 * subclass this basic matcher and provide a specific implementation for method
 * {@link BaseMatcher#isCorresponding(EObject, EObject)}
 */
public abstract class BaseMatcher implements IMatcher {

	/**
	 * The difference which will contain the matching.
	 */
	private SymmetricDifference matching;

	/**
	 * The A version of the model.
	 */
	private Resource modelA;

	/**
	 * The B version of the model.
	 */
	private Resource modelB;
	
	/**
	 * RESOURCE or RESOURCE_SET
	 */
	 private Scope scope;

	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB, Scope scope, boolean calculateReliability) {
		
		SymmetricDifference matching = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		matching.setUriModelA(modelA.getURI().toString());
		matching.setUriModelB(modelB.getURI().toString());
		
		addMatches(modelA, modelB, matching, scope, calculateReliability);
		
		return matching;
	}

	@Override
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability) {
		
		this.modelA = modelA;
		this.modelB = modelB;
		this.matching = matching;
		this.scope = scope;
		
		if (scope == Scope.RESOURCE_SET){
			// Include all ResourceSets A for matching			
			for (Resource r : modelA.getResourceSet().getResources()) {
				traverseResourceA(r);
			}
		} else {
			// Consider only resource model A
			traverseResourceA(modelA);
		}
	}
	
	@Override
	public boolean isResourceSetCapable() {
		return true;
	}
	
	private void traverseResourceA(Resource resourceA){
		EObject elementA = null;
		TreeIterator<EObject> iterA = resourceA.getAllContents();

		while (iterA.hasNext()) {
			elementA = iterA.next();
			
			if (scope == Scope.RESOURCE_SET){
				// Include all resource sets B for search
				for (Resource r : modelB.getResourceSet().getResources()) {
					EObject elementB = null;
					TreeIterator<EObject> iterB = r.getAllContents();

					boolean matchFound = false;
					while (iterB.hasNext()) {
						elementB = iterB.next();

						if (isCorresponding(elementA, elementB)) {							
							matching.addCorrespondence(elementA, elementB);
							matchFound = true;
							break;
						}
					}
					
					if (matchFound){
						break;
					}
				}
			} else {
				// Consider only resource model B for search
				EObject elementB = null;
				TreeIterator<EObject> iterB = modelB.getAllContents();

				while (iterB.hasNext()) {
					elementB = iterB.next();

					if (isCorresponding(elementA, elementB)) {																		
						matching.addCorrespondence(elementA, elementB);
						break;
					}
				}
			}	
		}
	} 
	
	/**
	 * Calculates matching between to objects. Subclasses have to implement this method!
	 * 
	 * @param elementA
	 *            the model A version of the object.
	 * @param elementB
	 *            the model B version of the object.
	 * @return true if the objects are corresponding to each other; false
	 *         otherwise.
	 */
	protected abstract boolean isCorresponding(EObject elementA, EObject elementB);

	/**
	 * Check if a correspondence for this object (already) exists.
	 * 
	 * @param obj
	 *            the object to test.
	 * @return true if the object belongs to a correspondence; false otherwise.
	 */
	protected boolean isCorresponding(EObject obj) {
		for (Correspondence c : matching.getCorrespondences()) {
			if (c.getObjA() == obj | c.getObjB() == obj) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return the difference which will contain the matching.
	 */
	protected SymmetricDifference getDifference() {
		return matching;
	}

	/**
	 * @return the A version of the model.
	 */
	protected Resource getModelA() {
		return modelA;
	}

	/**
	 * @return the B version of the model.
	 */
	protected Resource getModelB() {
		return modelB;
	}

}
