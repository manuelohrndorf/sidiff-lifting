package org.sidiff.patching.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.patching.ArgumentWrapper;
import org.sidiff.patching.IArgumentManager;

public abstract class AbstractBatchArgumentManager implements IArgumentManager {

	private Map<ObjectParameterBinding, ArgumentWrapper> argumentResolutions;

	public AbstractBatchArgumentManager() {
		argumentResolutions = new HashMap<ObjectParameterBinding, ArgumentWrapper>();
	}

	@Override
	public abstract Resource getOriginModel();

	@Override
	public abstract Resource getTargetModel();

	/**
	 * Template method which is to be implemented by subclasses.
	 * 
	 * @param originObject
	 * @return
	 */
	protected abstract EObject resolve(EObject originObject);

	@Override
	public ArgumentWrapper getArgument(ObjectParameterBinding binding) {
		if (argumentResolutions.get(binding) != null) {
			return argumentResolutions.get(binding);
		}

		ArgumentWrapper argument = new ArgumentWrapper(binding);
		argument.resolveTo(resolve(binding.getActualA()));
		argumentResolutions.put(binding, argument);

		return argument;
	}

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel) {
		// usually done in constructor of subclasses
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		// not needed in batch mode
		return null;
	}

	@Override
	public void addArgumentResolution(ObjectParameterBinding binding, EObject targetObject) {
		// not needed in batch mode
	}

	@Override
	public void resetArgumentResolution(ObjectParameterBinding binding) {
		// not needed in batch mode
	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		// not needed in batch mode
	}

	@Override
	public void setMinReliability(float minReliability) {
		// not needed in batch mode
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		// not needed in batch mode
		return 0;
	}

	@Override
	public boolean isModified(EObject targetObject) {
		// not needed in batch mode
		return false;
	}

}
