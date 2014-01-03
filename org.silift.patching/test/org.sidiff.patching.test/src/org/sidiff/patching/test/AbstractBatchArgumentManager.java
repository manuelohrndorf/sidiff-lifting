package org.sidiff.patching.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.silift.common.util.emf.Scope;

public abstract class AbstractBatchArgumentManager implements IArgumentManager {

	private Map<ObjectParameterBinding, ArgumentWrapper> argumentResolutions;

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
	public void init(AsymmetricDifference patch, Resource targetModel, Scope scope) {
		// init argument wrappers and provide initial resolutions
		argumentResolutions = new HashMap<ObjectParameterBinding, ArgumentWrapper>();
		for (OperationInvocation invocation : patch.getOperationInvocations()) {
			for (ParameterBinding binding : invocation.getParameterBindings()) {
				if (binding instanceof ObjectParameterBinding){
					ObjectParameterBinding objBinding = (ObjectParameterBinding) binding;
					ArgumentWrapper arg = new ArgumentWrapper(objBinding);
					if (objBinding.getActualA() != null){
						// try to resolve originObject
						EObject targetObject = resolve(objBinding.getActualA());
						arg.resolveTo(targetObject);
					}
					argumentResolutions.put(objBinding, arg);
				}
			}
		}
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		// not needed in batch mode
		return null;
	}

	@Override
	public void addArgumentResolution(ObjectParameterBinding binding, EObject targetObject) {
		argumentResolutions.get(binding).resolveTo(targetObject);
		
		// do also resolve target bindings to which binding is mapped
		for (ParameterMapping mapping : binding.getOutgoing()) {
			addArgumentResolution(mapping.getTarget(), targetObject);
		}
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
	public void addTargetObject(EObject targetObject) {
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
