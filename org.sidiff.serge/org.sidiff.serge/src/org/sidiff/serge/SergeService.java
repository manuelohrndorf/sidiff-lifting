package org.sidiff.serge;

import org.sidiff.serge.exceptions.EAttributeNotFoundException;
import org.sidiff.serge.exceptions.EClassifierUnresolvableException;
import org.sidiff.serge.exceptions.EPackageNotFoundException;

public interface SergeService {
	
	public void init(Class<?> service, String pathToConfig, String workspace_loc, String pathToOutputFolder) throws EClassifierUnresolvableException, EAttributeNotFoundException, EPackageNotFoundException, Exception;
	
	public void generate(Class<?> service) throws EPackageNotFoundException;
}
