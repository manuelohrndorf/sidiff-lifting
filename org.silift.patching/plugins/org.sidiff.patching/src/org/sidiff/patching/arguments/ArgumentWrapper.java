package org.sidiff.patching.arguments;

import org.sidiff.difference.asymmetric.ParameterBinding;


/**
 * Base class of all argument wrappers.
 * 
 * @author kehrer
 */
public abstract class ArgumentWrapper {

	private IArgumentManager argumentManager;

	public ArgumentWrapper(IArgumentManager argumentManager) {
		super();
		this.argumentManager = argumentManager;
	}

	protected IArgumentManager getArgumentManager() {
		return argumentManager;
	}
	
	public abstract ParameterBinding getParameterBinding();
	
	public abstract boolean isModified();
	
	public abstract boolean isResolved();
	
	public abstract boolean isDefaultValue();
	
}
