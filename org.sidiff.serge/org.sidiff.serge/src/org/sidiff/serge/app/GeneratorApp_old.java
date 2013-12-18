package org.sidiff.serge.app;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.serge.SergeService_old;
import org.sidiff.serge.impl.Activator_old;
import org.sidiff.serge.services.HenshinModuleGenerator_old;

public class GeneratorApp_old implements IApplication {
    
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		/** File and Folder loading ********************************************************************/
		
    	String FILE_SEPERATOR = System.getProperty("file.separator");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String OUTPUT_FOLDER_PATH	= argument[0] + FILE_SEPERATOR;
		String INPUT_CONFIG_PATH = argument[1];
		String INPUT_WORKSPACE_LOC = argument[2];		
		
		/*** Start generating rules*********************************************************************/
		
		SergeService_old generatingService = ServiceHelper.getService(Activator_old.getContext(), SergeService_old.class);
		generatingService.init(HenshinModuleGenerator_old.class, INPUT_CONFIG_PATH, INPUT_WORKSPACE_LOC, OUTPUT_FOLDER_PATH);
		generatingService.generate(HenshinModuleGenerator_old.class);
		
				
		return null;
	}

	@Override
	public void stop() {


	}
	

}
