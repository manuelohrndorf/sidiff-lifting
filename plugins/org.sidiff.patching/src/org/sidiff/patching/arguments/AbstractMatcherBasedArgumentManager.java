package org.sidiff.patching.arguments;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.access.EObjectLocation;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.Matching;
import org.sidiff.matching.model.MatchingModelFactory;

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
		// TODO Fix this
//		EObject originObject = binding.getActualA();
//		if (originObject != null && matchingOriginTarget.getCorrespondingObjectInB(originObject) == targetObject) {
//			return matchingOriginTarget.getReliability(originObject, targetObject);
//		}

		return 0.0f;
	}
	
	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		// Lazy calculate the matching
		if (matchingOriginTarget == null){
			
			matcher.reset();
			matcher.startMatching(Arrays.asList(getOriginModel(), getTargetModel()), getScope());	

			// Get Matching
			// In SiLift we assume support of @see{MatchingModelCorrespondences}
			ICorrespondences correspondences = matcher.getCorrespondencesService();
			Matching matching = ((MatchingModelCorrespondences)correspondences).getMatching();	
		
			if (!(matching.getCorrespondences().size() != 0)) {
				try {
					throw new NoCorrespondencesException();
				} catch (NoCorrespondencesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Contain Matching in Difference
			SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
			symmetricDiff.setMatching(matching);
			matchingOriginTarget = symmetricDiff;
			
			matcher.reset();

		}
		
		if(matchingChangedTarget == null){
			
			matcher.reset();			
			matcher.startMatching(Arrays.asList(getChangedModel(), getTargetModel()), getScope());	

			// Get Matching
			// In SiLift we assume support of @see{MatchingModelCorrespondences}
			ICorrespondences correspondences = matcher.getCorrespondencesService();
			Matching matching = ((MatchingModelCorrespondences)correspondences).getMatching();	
		
			if (!(matching.getCorrespondences().size() != 0)) {
				try {
					throw new NoCorrespondencesException();
				} catch (NoCorrespondencesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Contain Matching in Difference
			SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();
			symmetricDiff.setMatching(matching);
			matchingChangedTarget = symmetricDiff;
			
			matcher.reset();

			}
		
		if (matchingOriginTarget.getCorrespondingObjectInB(originObject) != null) {
			return matchingOriginTarget.getCorrespondingObjectInB(originObject);
		} else if(matchingChangedTarget.getCorrespondingObjectInB(originObject) != null){
			return matchingChangedTarget.getCorrespondingObjectInB(originObject);
		}

		EObjectLocation location = EMFResourceUtil.locate(getOriginModel(), originObject);

		if (location == EObjectLocation.PACKAGE_REGISTRY) {
			Correspondence c = MatchingModelFactory.eINSTANCE.createCorrespondence();
			c.setMatchedA(originObject);
			c.setMatchedB(originObject);
			matchingOriginTarget.addCorrespondence(c);
			return originObject;
		}
		if (location == EObjectLocation.RESOURCE_SET_INTERNAL) {
			// TODO (TK)
		}

		return null;
	}
	
	
	
}
