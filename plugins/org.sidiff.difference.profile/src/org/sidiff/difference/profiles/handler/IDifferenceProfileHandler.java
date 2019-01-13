package org.sidiff.difference.profiles.handler;

import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ITypedExtension;
import org.sidiff.common.extension.TypedExtensionManager;

/**
 * 
 * @author cpietsch
 * 
 * This interface has to be implemented for every document type that supports a profile-mechanism.
 *
 */
public interface IDifferenceProfileHandler extends ITypedExtension {
	
	Description<IDifferenceProfileHandler> DESCRIPTION = Description.of(IDifferenceProfileHandler.class,
			"org.sidiff.difference.profile.difference_profile_handler", "difference_profile", "profile_handler");

	TypedExtensionManager<IDifferenceProfileHandler> MANAGER = new TypedExtensionManager<>(DESCRIPTION);


	/**
	 * checks if a profile has been applied on the given resource
	 * 
	 * @param resource
	 * @return <code>true</code> if a profile has been applied, <code>false</code> otherwise
	 */
	public boolean isProfiled(Resource resource);
	
	/**
	 * Returns the <b>base type/s</b> of the profiled document.
	 * @return the base type/s of the profiled document
	 */
	@Override
	Set<String> getDocumentTypes();
	
	/**
	 * 
	 * @param resource
	 * @return the type of every profile that has been applied on the given resource
	 */
	public Set<String> getProfileTypes(Resource resource);
}
