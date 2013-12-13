package org.sidiff.patching;

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
	
	public ArgumentWrapper(ObjectParameterBinding binding) {
		super();
		this.binding = binding;
	}
	
	
	public boolean isResolved(){
		return targetObject != null;
	}

	public EObject getTargetObject(){
		assert (isResolved());
		return targetObject;
	}
	
	public EObject getProxyObject(){
		assert (!isResolved());
		if (binding.getActualA() != null){
			return binding.getActualA();
		} else {
			return binding.getActualB();
		}
	}
	
	public void resolveTo(EObject targetObject){
		this.targetObject = targetObject;
	}
	
	public void resetResolution(){
		targetObject = null;
	}
	
}
