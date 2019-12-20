package org.sidiff.patching.arguments;

import java.util.Objects;

import org.sidiff.difference.asymmetric.ParameterBinding;

/**
 * Base class of all argument wrappers.
 * 
 * @author kehrer
 */
public abstract class ArgumentWrapper {

	private IArgumentManager argumentManager;

	public ArgumentWrapper(IArgumentManager argumentManager) {
		this.argumentManager = Objects.requireNonNull(argumentManager);
	}

	protected IArgumentManager getArgumentManager() {
		return argumentManager;
	}

	public abstract ParameterBinding getParameterBinding();
	public abstract boolean isModified();
	public abstract boolean isResolved();
}
