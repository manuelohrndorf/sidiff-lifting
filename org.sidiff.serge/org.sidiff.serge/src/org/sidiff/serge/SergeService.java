package org.sidiff.serge;

import org.sidiff.serge.exceptions.EClassUnresolvableException;

public interface SergeService {
	
	public void init(Class<?> service, String pathToConfig, String workspace_loc, String pathToOutputFolder) throws EClassUnresolvableException;
	
	public void generate(Class<?> service);
}
