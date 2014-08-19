package org.sidiff.patching.batch.arguments;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.patching.arguments.AbstractMatcherBasedArgumentManager;

public class BatchMatcherBasedArgumentManager extends AbstractMatcherBasedArgumentManager {

	public BatchMatcherBasedArgumentManager(IMatcher matcher) {
		super(matcher);
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
