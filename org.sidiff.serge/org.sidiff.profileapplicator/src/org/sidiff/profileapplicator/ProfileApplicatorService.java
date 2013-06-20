package org.sidiff.profileapplicator;

public interface ProfileApplicatorService {
	
	public void init(Class<?> service, String package_loc, String pathToConfig, String pathToInputFolder, String pathToOutputFolder);
	
	public void applyProfile(Class<?> service);
}
