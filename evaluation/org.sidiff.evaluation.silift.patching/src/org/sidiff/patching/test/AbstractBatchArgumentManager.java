package org.sidiff.patching.test;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.BaseArgumentManager;

public abstract class AbstractBatchArgumentManager extends BaseArgumentManager {

	@Override
	public Resource getTargetModel() {
		return super.getTargetModel();
	}
	
	@Override
	public void setArgument(ValueParameterBinding binding, Object value) {
		// Not needed in batch mode.
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		// Not needed in batch mode.
		return null;
	}

	@Override
	public void resetArgumentResolution(ObjectParameterBinding binding) {
		// Not needed in batch mode.
	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		// Not needed in batch mode.
	}

	@Override
	public void addTargetObject(EObject targetObject) {
		// Not needed in batch mode.
	}
}
