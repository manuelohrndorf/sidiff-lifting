package org.sidiff.editrule.generator.serge.batch;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.editrule.generator.serge.Serge;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

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
		String OUTPUT_FOLDER = argument[1];
			
		//Init default settings
		SergeSettings settings = new SergeSettings(OUTPUT_FOLDER, INPUT_CONFIG_PATH, false);
		
		Serge serge = new Serge();
		serge.init(settings,new NullProgressMonitor());
		serge.generateEditRules(new NullProgressMonitor());
				
		return null;
	}

	@Override
	public void stop() {
		// do nothing

	}
}