package org.sidiff.patching.arguments;

import java.util.LinkedList;
import java.util.List;

import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class MultiArgumentWrapper extends ArgumentWrapper {

	/**
	 * The binding from the asymmetric difference 
	 */
	private MultiParameterBinding binding;

	/**
	 * Dynamic list of nested object argument wrappers
	 */
	private List<ObjectArgumentWrapper> nestedWrappers;

	public MultiArgumentWrapper(MultiParameterBinding binding, IArgumentManager argumentManager) {
		super(argumentManager);
		this.binding = binding;

		// nested object argument wrappers
		nestedWrappers = new LinkedList<ObjectArgumentWrapper>();
		for (ParameterBinding nestedBinding : binding.getParameterBindings()) {
			ObjectParameterBinding nestedObjectBinding = (ObjectParameterBinding) nestedBinding;
			nestedWrappers.add(new ObjectArgumentWrapper(nestedObjectBinding, argumentManager));
		}
	}

	public MultiParameterBinding getMultiParameterBinding() {
		return binding;
	}

	@Override
	public ParameterBinding getParameterBinding() {
		return getMultiParameterBinding();
	}

	@Override
	public boolean isModified() {
		for (ObjectArgumentWrapper nestedWrapper : nestedWrappers) {
			if (nestedWrapper.isModified()){
				return true;
			}
		}
		
		return false;
	}

	public List<ObjectArgumentWrapper> getNestedWrappers() {
		return nestedWrappers;
	}

	@Override
	public boolean isResolved() {
		for (ObjectArgumentWrapper nestedWrapper : nestedWrappers) {
			if (!nestedWrapper.isResolved()){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isDefaultValue() {
		//Objects do not have a default value
		return false;
	}
}
