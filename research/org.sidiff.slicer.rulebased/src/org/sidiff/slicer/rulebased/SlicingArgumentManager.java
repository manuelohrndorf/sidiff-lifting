package org.sidiff.slicer.rulebased;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.BaseArgumentManager;

/**
 * 
 * @author cpietsch
 *
 */
public class SlicingArgumentManager extends BaseArgumentManager {
	
	private Map<EObject, EObject> correspondences;
	
	public SlicingArgumentManager(Map<EObject,EObject> correspondences) {
		this.correspondences = correspondences;
	}

	@Override
	public void setArgument(ValueParameterBinding binding, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetArgumentResolution(ObjectParameterBinding binding) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTargetObject(EObject targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		return originObject;
	}

	public Map<EObject, EObject> getCorrespondences() {
		return correspondences;
	}

	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean canResolveArguments(AsymmetricDifference asymmetricDifference, Resource targetModel) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
