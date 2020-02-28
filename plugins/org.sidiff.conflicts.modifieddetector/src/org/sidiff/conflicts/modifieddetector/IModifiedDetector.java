package org.sidiff.conflicts.modifieddetector;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.common.extension.TypedExtensionManager;

/**
 * This interface belongs to the 'org.silift.modifieddetector'
 * extension point. This extension point is used to add a new modified detector
 * to the merging functionality of SiLift. 
 * A plug-in that adds this extension point has to implement
 * this interface.
 * @author dreuling
 */
public interface IModifiedDetector extends ITypedExtension {

	Description<IModifiedDetector> DESCRIPTION = Description.of(IModifiedDetector.class,
			"org.sidiff.conflicts.modifieddetector", "modifieddetector", "class");

	TypedExtensionManager<IModifiedDetector> MANAGER = new TypedExtensionManager<>(DESCRIPTION);

	/**
	 * Initialize method: 
	 * @param modelA the modelA to use
	 * @param modelB the modelB to use
	 * @param matcher the matcher to use
	 * @param scope the scope to use
	 * @throws IOException 
	 */
	void init(Resource modelA, Resource modelB, IModifiedDetectorSettings settings) throws IOException;

	/**
	 * 
	 * @param targetObject to test if it has been modified
	 * @return whether the object has been modified
	 */
	boolean isModified(EObject targetObject);
}
