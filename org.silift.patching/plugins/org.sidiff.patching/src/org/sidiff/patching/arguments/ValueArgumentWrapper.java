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
	
	public ValueParameterBinding getValueBinding(){
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


	@Override
	public boolean isDefaultValue() {

		//Check if there is a default value at all
		if(this.binding.getFormalParameter().getType().getDefaultValue() == null){
			//Check if there is a value at all
			if(value == null){
				return true;
			}
			else{
				return false;
			}
		}
		
		//Check whether the current value equals the default value
		else{
			return this.binding.getFormalParameter().getType().getDefaultValue().toString().equals(value);
		}
	}
}
