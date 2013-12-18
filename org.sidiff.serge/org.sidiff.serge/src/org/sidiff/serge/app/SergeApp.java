package org.sidiff.serge.app;

import org.sidiff.serge.exceptions.EPackageNotFoundException;
import org.sidiff.serge.impl.Serge;

/**
 * This class should be used if SERGe is run as an Eclipse Application.
 * @author mrindt
 *
 */
public class SergeApp {

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
