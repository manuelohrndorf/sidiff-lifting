package org.sidiff.serge.services;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.sidiff.serge.impl.Serge;

/**
 * This class should be used if SERGe is run as an Service.
 * @author mrindt
 *
 */
public class SergeService implements IApplication{
	
	
	@Override
	public Object start(IApplicationContext context) throws Exception {

		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String INPUT_CONFIG_PATH = argument[0];
		
		run(INPUT_CONFIG_PATH);
		
		
		return null;
	}

	@Override
	public void stop() {
		// do nothing
		
	}
	
	/**
	 * Initializes and runs the generation process.
	 * Requires a SERGe configuration.
	 *  
	 * @param pathToConfig
	 */
	public void run(String pathToConfig) {	

		try {
			
			Serge serge = new Serge();
			serge.init(pathToConfig);
			serge.generate();
		
		} catch (EPackageNotFoundException e) {
			
			e.printStackTrace();
			
		}
	}

}
