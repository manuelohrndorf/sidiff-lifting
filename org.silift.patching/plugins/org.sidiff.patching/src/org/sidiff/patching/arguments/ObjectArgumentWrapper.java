package org.sidiff.patching.arguments;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class ObjectArgumentWrapper extends ArgumentWrapper {
	
	/**
	 * The object parameter binding from the asymmetric difference.
	 */
	private ObjectParameterBinding binding;

	/**
	 * The resolved object in the target model.
	 */
	private EObject targetObject;

	/**
	 * Is the argument already resolved?
	 */
	private boolean resolved;
	
	
	public ObjectArgumentWrapper(ObjectParameterBinding binding, IArgumentManager argumentManager) {
		super(argumentManager);
		this.binding = binding;
		resolved = false;
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
		assert(targetObject != null);
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
	
	@Override
	public boolean isResolved() {
		return resolved;
	}
	
	public ObjectParameterBinding getObjectBinding(){
		return binding;
	}
	
	@Override
	public boolean isModified(){
		if (!resolved){
			// not resolved, so modification check makes no sense
			return false;
		} else {
			return getArgumentManager().isModified(targetObject);
		}
	}
	

	@Override
	public boolean isDefaultValue() {
		//Objects do not have a default value
		return false;
	}
	
	@Override
	public ParameterBinding getParameterBinding() {
		return getObjectBinding();
	}
}
