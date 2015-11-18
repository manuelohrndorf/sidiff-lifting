package org.sidiff.patching.arguments;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matcher.IMatcher;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;

public abstract class AbstractMatcherBasedArgumentManager extends BaseArgumentManager {

	/**
	 * The matcher to be used to compute the correspondences between originModel
	 * and targetModel.
	 */
	private IMatcher matcher;

	/**
	 * Correspondences between originModel and targetModel.
	 */
	private SymmetricDifference matchingOriginTarget;
	
	/**
	 * Correspondences between changedModel and targetModel.
	 */
	private SymmetricDifference matchingChangedTarget;

	public AbstractMatcherBasedArgumentManager(IMatcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		EObject originObject = binding.getActualA();
		if (originObject != null && matchingOriginTarget.getCorrespondingObjectInB(originObject) == targetObject) {
			return matchingOriginTarget.getReliability(originObject, targetObject);
		}

		return 0.0f;
	}
	
	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		// Lazy calculate the matching
		if (matchingOriginTarget == null){
			matchingOriginTarget = matcher.createMatching(getOriginModel(), getTargetModel(), getScope(), true);
		}
		
		if(matchingChangedTarget == null){
			matchingChangedTarget = matcher.createMatching(getChangedModel(), getTargetModel(), getScope(), true);
		}
		
		if (matchingOriginTarget.getCorrespondingObjectInB(originObject) != null) {
			return matchingOriginTarget.getCorrespondingObjectInB(originObject);
		} else if(matchingChangedTarget.getCorrespondingObjectInB(originObject) != null){
			return matchingChangedTarget.getCorrespondingObjectInB(originObject);
		}

		EObjectLocation location = EMFResourceUtil.locate(getOriginModel(), originObject);

		if (location == EObjectLocation.PACKAGE_REGISTRY) {
			Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(originObject, originObject);
			c.setReliability(1.0f);			
			matchingOriginTarget.addCorrespondence(c);
			return originObject;
		}
		if (location == EObjectLocation.RESOURCE_SET_INTERNAL) {
			// TODO (TK)
		}

		return null;
	}
	
	
	
}
