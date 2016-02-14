package org.sidiff.conflicts.modifieddetector;

import java.io.FileNotFoundException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.matcher.IMatcher;

/**
 * This interface belongs to the 'org.silift.modifieddetector'
 * extension point. This extension point is used to add a new modified detector
 * to the merging functionality of SiLift. 
 * A plug-in that adds this extension point has to implement
 * this interface.
 * @author dreuling
 *
 */
public interface IModifiedDetector {
	
	/**
	 * The shared extension point id.
	 */
	public static final String EXTENSION_POINT_ID = "org.sidiff.conflicts.modifieddetector";	
	
	/**
	 * The shared executable point
	 */
	public static final String EXECUTABLE = "class";	
	
	/**
	 * Returns the description name of the modified detector.
	 * 
	 * @return the modified detector name.
	 */
	public String getName();
	
	/**
	 * Returns the short name (used as a key) of the modified detector.
	 * 
	 * @return the modified detector short name (used as key).
	 */
	public String getKey();
	
	/**
	 * Initialize method: 
	 * @param modelA the modelA to use
	 * @param modelB the modelB to use
	 * @param matcher the matcher to use
	 * @param scope the scope to use
	 * @throws FileNotFoundException 
	 */
	public void init(Resource modelA, Resource modelB, IMatcher matcher, Scope scope) throws FileNotFoundException;

	/**
	 * 
	 * @param targetObject to test if it has been modified
	 * @return whether the object has been modified
	 */
	public boolean isModified(EObject targetObject);
	
	/**
	 * @return the document type the modified detector is primarily implemented
	 *         for.
	 */
	public String getDocumentType();
	
	/**
	 * Returns whether this modified detector can handle models of the given
	 * documentType.
	 * 
	 * @param docType
	 * @return
	 */
	public boolean canHandle(String docType);

}
