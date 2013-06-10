package org.sidiff.serge;

public interface SergeService {
	
	public void init(Class<?> service, String pathToConfig, String workspace_loc, String pathToOutputFolder);
	
	public void generate(Class<?> service);
}
