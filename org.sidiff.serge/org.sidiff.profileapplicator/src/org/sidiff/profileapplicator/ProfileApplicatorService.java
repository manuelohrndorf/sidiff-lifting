package org.sidiff.profileapplicator;

/**
 * This class implements the profile applicator which
 * make use of Henshin Higher Order Transformations (HOT)
 * for manipulating exisiting edit rules for support of stereotypes/profiles
 * 
 * @author dreuling
 *
 */

public interface ProfileApplicatorService {
	
	public void init(Class<?> service, String pathToConfig, String pathToInputFolder, String pathToOutputFolder);
	
	public void applyProfile(Class<?> service);
}
