package org.sidiff.profileapplicator;

public interface ProfileApplicatorService {
	
	public void init(Class<?> service, String pathToConfig, String pathToInputFolder, String pathToOutputFolder);
	
	public void applyProfile(Class<?> service);
}
