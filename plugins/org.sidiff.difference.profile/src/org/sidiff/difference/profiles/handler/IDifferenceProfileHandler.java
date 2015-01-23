package org.sidiff.difference.profiles.handler;

import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * 
 * @author cpietsch
 * 
 * This interface has to be implemented for every document type that supports a profile-mechanism.
 *
 */
public interface IDifferenceProfileHandler {

	public final String extensionPointID = "org.sidiff.difference.profile.difference_profile_handler";
	
	/**
	 * checks if a profile has been applied on the given resource
	 * 
	 * @param resource
	 * @return <code>true</code> if a profile has been applied, <code>false</code> otherwise
	 */
	public boolean isProfiled(Resource resource);
	
	/**
	 * 
	 * @return the base type of the profiled document
	 */
	public String getBaseType();
	
	/**
	 * 
	 * @param resource
	 * @return the type of every profile that has been applied on the given resource
	 */
	public Set<String> getProfileTypes(Resource resource);
}
