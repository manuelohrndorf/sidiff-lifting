package org.sidiff.matcher;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;

/**
 * An abstract implementation of the IMatcher interface.
 * A subclass has to implement the methods {@link #compareResources()},
 * {@link #getDocumentType()}, {@link #canHandle(Resource, Resource)}
 * and {@link #canComputeReliability()}.
 * 
 *@author cpietsch
 *
 */
public abstract class AbstractMatcher implements IMatcher {

	/**
	 * The difference which will contain the matching.
	 */
	protected SymmetricDifference matching;

	/**
	 * The A version of the model.
	 */
	protected Resource modelA;

	/**
	 * The B version of the model.
	 */
	protected Resource modelB;

	/**
	 * RESOURCE or RESOURCE_SET
	 */
	protected Scope scope;
	
	/**
	 * flag for reliability calculation
	 */
	protected boolean calculateReliability;
	
	@Override
	public SymmetricDifference createMatching(Resource modelA, Resource modelB,
			Scope scope, boolean calculateReliability) {
		SymmetricDifference matching = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		matching.setUriModelA(modelA.getURI().toString());
		matching.setUriModelB(modelB.getURI().toString());
		
		addMatches(modelA, modelB, matching, scope, calculateReliability);

		return matching;
	}
	
	@Override
	public void addMatches(Resource modelA, Resource modelB, SymmetricDifference matching, Scope scope,
			boolean calculateReliability){
		
		this.matching = matching;
		this.modelA = modelA;
		this.modelB = modelB;
		this.scope = scope;
		this.calculateReliability = calculateReliability;
		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------------------- Add Matches ---------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "Matcher: " + getClass().getSimpleName());
		LogUtil.log(LogEvent.NOTICE, "Model A: " + modelA);
		LogUtil.log(LogEvent.NOTICE, "Model B: " + modelB);
		LogUtil.log(LogEvent.NOTICE, "Scope: " + scope);
		LogUtil.log(LogEvent.NOTICE, "Calculate reliability: " + calculateReliability);
		
		compareResources();
	}
	
	protected abstract void compareResources();

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// generic matchers can handle every model
		if (getDocumentType().equals(EMFModelAccess.GENERIC_DOCUMENT_TYPE)) {
			return true;
		}

		if (isResourceSetCapable()) {
			Set<String> docTypesA = EMFModelAccess.getDocumentTypes(modelA,
					Scope.RESOURCE_SET);
			Set<String> docTypesB = EMFModelAccess.getDocumentTypes(modelB,
					Scope.RESOURCE_SET);

			return docTypesA.contains(getDocumentType())
					&& docTypesB.contains(getDocumentType());
		} else {
			String docTypeA = EMFModelAccess
					.getCharacteristicDocumentType(modelA);
			String docTypeB = EMFModelAccess
					.getCharacteristicDocumentType(modelB);

			return docTypeA.equals(getDocumentType())
					&& docTypeB.equals(getDocumentType());
		}
	}
	
	/**
	 * Check if a correspondence for this object (already) exists.
	 * 
	 * @param obj
	 *            the object to test.
	 * @return true if the object belongs to a correspondence; false otherwise.
	 */
	protected boolean hasCorrespondence(EObject obj) {
		for (Correspondence c : matching.getCorrespondences()) {
			if (c.getObjA() == obj | c.getObjB() == obj) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isResourceSetCapable() {
		return true;
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
