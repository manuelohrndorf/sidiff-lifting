package org.sidiff.patching;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface IPatchCorrespondence {

	String EXTENSION_POINT_ID = "org.sidiff.patching.correspondence";
	String DOCUMENT_TYPE = "documentType";
	String EXECUTEBALE = "class";
	String DEFAULT_DOCUMENT_TYPE = "*";

	public void set(Resource originModel, Resource targetModel);

	/**
	 * @return the origin model
	 */
	public Resource getOriginModel();

	/**
	 * @return the model on which the patch should be applied
	 */
	public Resource getTargetModel();

	/**
	 * For a given originObject (i.e. object of the origin model), this method
	 * returns the corresponding object in the target model.<br/>
	 * This method may return <code>null</code> if there is no corresponding
	 * object in the target model at all.
	 * 
	 * @param originObject
	 *            eObject in origin model.
	 * @return the correspondent eObject in the target model.
	 */
	public EObject getCorrespondence(EObject originObject);

	/**
	 * Get the potential arguments for a given originObject that has no
	 * corresponding object in target model.
	 * 
	 * @param eObject
	 * @return the possible arguments, categorized by their Resource.
	 */
	public Map<Resource, Collection<EObject>> getPotentialArguments(EObject originObject);

	/**
	 * Adds a new correspondence to the patch correspondence.
	 * 
	 * @param originObject
	 *            Object from the origin model.
	 * @param targetObject
	 *            Object from the target model.
	 */
	public void setCorrespondence(EObject originObject, EObject targetObject);

	/**
	 * Removes the correspondence for originObject
	 * 
	 * @param eObject
	 */
	public void removeCorrespondence(EObject originObject);

	/**
	 * Called when an object is added to the target model.
	 * 
	 * @param eObject
	 */
	public void addNewEObject(EObject targetObject);

	/**
	 * Called when an object is removed from the target model.
	 * 
	 * @param eObject
	 */
	public void removeEObject(EObject targetObject);

	/**
	 * Minimal reliability for correspondences
	 * 
	 * @param minReliability
	 */
	public void setMinReliability(float minReliability);

	/**
	 * Gets the reliability value for this eObjects
	 * 
	 * @param objectA
	 * @param objectB
	 * @return a reliability value
	 */
	public float getReliability(EObject objectA, EObject objectB);

	/**
	 * 
	 * @param object
	 * @return
	 */
	public boolean isModified(EObject object);

}