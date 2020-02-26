package org.sidiff.patching.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.model.util.MatchingModelUtil;

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

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, IArgumentManagerSettings settings) {
		this.matcher = Objects.requireNonNull(settings.getMatcher(), "Matcher must be set");
		this.matchingOriginTarget = null;
		this.matchingChangedTarget = null;
		super.init(patch, targetModel, settings);
	}

	protected SymmetricDifference initMatching(Collection<Resource> models) {
		// SiLift always uses MatchingModelCorrespondences
		MatchingModelCorrespondences correspondences =
			ICorrespondences.MANAGER.getExtension(MatchingModelCorrespondences.class).orElseThrow(
				() -> new IllegalStateException("No correspondences service is available"));

		matcher.reset();
		matcher.setCorrespondencesService(correspondences);
		matcher.startMatching(models, getScope());	

		// Contain Matching in Difference
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
		symmetricDiff.setMatching(correspondences.getMatching());
		return symmetricDiff;
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		// TODO Fix this
//		EObject originObject = binding.getActualA();
//		if (originObject != null && matchingOriginTarget.getCorrespondingObjectInB(originObject) == targetObject) {
//			return matchingOriginTarget.getReliability(originObject, targetObject);
//		}
		return 0.0f;
	}
	
	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		if(matchingOriginTarget == null) {
			matchingOriginTarget = initMatching(Arrays.asList(getOriginModel(), getTargetModel()));
		}
		if(matchingOriginTarget.getCorrespondingObjectInB(originObject) != null) {
			return matchingOriginTarget.getCorrespondingObjectInB(originObject);
		}

		if(matchingChangedTarget == null) {
			matchingChangedTarget = initMatching(Arrays.asList(getChangedModel(), getTargetModel()));
		}
		if(matchingChangedTarget.getCorrespondingObjectInB(originObject) != null) {
			return matchingChangedTarget.getCorrespondingObjectInB(originObject);
		}

		switch(EMFResourceUtil.locate(getOriginModel(), originObject)) {
			case PACKAGE_REGISTRY:
				matchingOriginTarget.addCorrespondence(MatchingModelUtil.createCorrespondence(originObject, originObject));
				return originObject;
				
			case RESOURCE_SET_INTERNAL:
				Resource targetResource = getTargetModel().getResourceSet().getResource(originObject.eResource().getURI(), true);
				EObject targetObject = targetResource.getEObject(EcoreUtil.getURI(originObject).fragment());
				matchingOriginTarget.addCorrespondence(MatchingModelUtil.createCorrespondence(originObject, targetObject));
				return originObject;
			default:
				return null;
		}

	}

	@Override
	public boolean canResolveArguments(AsymmetricDifference asymmetricDifference, Resource targetModel, IArgumentManagerSettings settings) {
		return settings.getMatcher() != null && EMFModelAccess.getCharacteristicDocumentType(targetModel)
				.equals(EMFModelAccess.getCharacteristicDocumentType(asymmetricDifference.getOriginModel()));
	}
}
