package org.sidiff.patching.arguments;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;

/**
 * Wrapper class for operation arguments. Manages the resolution state of
 * exactly one ObjectParameterBinding that maps formal and actual parameters
 * within an asymmetric difference.
 * 
 * @author kehrer
 */
public class ArgumentWrapper {

	/**
	 * The object parameter binding from the asymmetric difference.
	 */
	private ObjectParameterBinding binding;

	/**
	 * The resolved object in the target model.
	 */
	private EObject targetObject;

	private boolean resolved;
	
	public ArgumentWrapper(ObjectParameterBinding binding) {
		super();
		this.binding = binding;
		resolved = false;
	}

	public boolean isResolved() {
		return resolved;
	}

	/**
	 * Returns the target object to which this argument wrapper is currently
	 * resolved. Note that the obtained target object is only valid if the
	 * current state of this wrapper is "resolved".
	 * 
	 * @return
	 */
	public EObject getTargetObject() {
		return targetObject;
	}

	public EObject getProxyObject() {
		if (binding.getActualA() != null) {
			return binding.getActualA();
		} else {
			return binding.getActualB();
		}
	}

	public void resolveTo(EObject targetObject) {
		this.targetObject = targetObject;
		resolved = true;
	}

	public void resetResolution() {
		resolved = false;
	}
	
	public void restoreResolution() {
		assert (targetObject != null);
		resolved = true;
	}
}
