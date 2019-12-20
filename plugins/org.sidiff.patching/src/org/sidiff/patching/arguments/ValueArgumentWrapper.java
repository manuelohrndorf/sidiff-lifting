package org.sidiff.patching.arguments;

import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;

public class ValueArgumentWrapper extends ArgumentWrapper {

	private ValueParameterBinding binding;
	
	private String value;
	
	public ValueArgumentWrapper(ValueParameterBinding binding, IArgumentManager argumentManager) {
		super(argumentManager);
		this.binding = binding;
		this.value = binding.getActual();
	}
	
	public ValueParameterBinding getValueBinding() {
		return binding;
	}
	
	@Override
	public ParameterBinding getParameterBinding() {
		return getValueBinding();
	}
	
	@Override
	public boolean isModified() {
		// Modification check not useful for value arguments
		return false;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean isResolved() {
		// Always resolved..
		return true;
	}
}
