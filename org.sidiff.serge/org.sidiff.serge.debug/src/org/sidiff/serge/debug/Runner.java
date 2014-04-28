package org.sidiff.serge.debug;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.serge.Serge;
import org.sidiff.serge.exceptions.EPackageNotFoundException;

/**
 * This class should be used if SERGe is run with OSGi for debug/testing purpose.
 * @author mrindt
 *
 */
public class Runner implements IApplication{


	@Override
	public Object start(IApplicationContext context) throws Exception {

		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String INPUT_CONFIG_PATH = argument[0];
		
		Serge serge = new Serge(INPUT_CONFIG_PATH);
		serge.generate();
				
		return null;
	}

	@Override
	public void stop() {
		// do nothing

	}
}