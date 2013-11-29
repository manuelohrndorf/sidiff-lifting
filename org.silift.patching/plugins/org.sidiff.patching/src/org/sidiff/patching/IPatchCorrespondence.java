package org.sidiff.patching;

import java.util.Collection;

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
	 * Returns the best fitting corresponded object. If eObject is part of an
	 * "external" model it must be returned as it is.
	 * 
	 * @param eObject
	 * @return the correspondent eObject
	 */
	public EObject getCorrespondence(EObject eObject);

	/**
	 * Returns all correspondences. If eObject is part of an "external" model it
	 * must be returned as it is.
	 * 
	 * @param eObject
	 * @return the correspondent eObjects
	 */
	public Collection<EObject> getAllCorrespondences(EObject eObject);

	/**
	 * Adds a new correspondence to the patch
	 * 
	 * @param elementA
	 *            Object from Model A
	 * @param elementB
	 *            Object from Model A'
	 */
	public void setCorrespondence(EObject elementA, EObject elementB);

	/**
	 * Removes all correspondences of this object
	 * 
	 * @param eObject
	 */
	public void removeCorrespondence(EObject eObject);

	/**
	 * Adds a new unmatched EObject to the list of correspondences
	 * 
	 * @param eObject
	 */
	public void addNewEObject(EObject eObject);

	/**
	 * Removes a eObject
	 * 
	 * @param eObject
	 */
	public void removeEObject(EObject eObject);

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