package org.sidiff.patching.arguments;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.silift.common.util.emf.Scope;

/**
 * The argument manager is the central interface for the patch engine in order
 * to resolve operation arguments on the target model. In principal, there are
 * several ways to do the argument resolution. An implementation must be
 * provided by concrete applications. Note that if the patch application is
 * non-interactive, several methods of this interface can have empty
 * implementations.
 * 
 * @author kehrer
 */
public interface IArgumentManager {

	public void init(AsymmetricDifference patch, Resource targetModel, Scope scope);

	/**
	 * @return the origin model
	 */
	public Resource getOriginModel();

	/**
	 * @return the model on which the patch should be applied
	 */
	public Resource getTargetModel();

	/**
	 * For a given parameter binding of the patch, this method returns the
	 * corresponding argument wrapper for the target model.
	 * 
	 * @param binding
	 *            parameter binding of the patch
	 * @return argument wrapper for the target model.
	 */
	public ArgumentWrapper getArgument(ObjectParameterBinding binding);

	/**
	 * Get the potential arguments for a given object parameter binding.
	 * 
	 * @param binding
	 * @return the possible arguments, categorized by their Resource.
	 */
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding);

	/**
	 * Adds a new argument resolution for a given parameter binding to the
	 * argument manager.
	 * 
	 * @param binding
	 *            Parameter binding of the patch.
	 * @param targetObject
	 *            Object from the target model to which the argument shall be
	 *            resolved.
	 */
	public void addArgumentResolution(ObjectParameterBinding binding, EObject targetObject);

	/**
	 * Removes the resolved targetObject for this binding, i.e., the respective
	 * argument wrapper is being reset.
	 * 
	 * @param eObject
	 */
	public void resetArgumentResolution(ObjectParameterBinding binding);

	/**
	 * Called when an object is removed from the target model.
	 * 
	 * @param eObject
	 */
	public void removeTargetObject(EObject targetObject);

	/**
	 * Called when an object is added to the target model.
	 * 
	 * @param targetObject
	 */
	public void addTargetObject(EObject targetObject);

	/**
	 * Minimal reliability for correspondences
	 * 
	 * @param minReliability
	 */
	public void setMinReliability(float minReliability);

	/**
	 * Gets the reliability value for an argument (i.e. object in target model)
	 * in terms of an object parameter binding.
	 * 
	 * @param binding
	 * @param targetObject
	 * @return a reliability value
	 */
	public float getReliability(ObjectParameterBinding binding, EObject targetObject);

	/**
	 * Checks whether a target object has been modified (compared to its
	 * corresponding in the origin model) or not.
	 * 
	 * @param targetObject
	 * @return
	 */
	public boolean isModified(EObject targetObject);

}