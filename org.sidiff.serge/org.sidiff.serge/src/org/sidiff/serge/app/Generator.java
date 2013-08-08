package org.sidiff.serge.app;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.serge.SergeService;
import org.sidiff.serge.impl.Activator;
import org.sidiff.serge.services.HenshinModuleGenerator;

public class Generator implements IApplication {
    
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		/** File and Folder loading ********************************************************************/
		
    	String FILE_SEPERATOR = System.getProperty("file.separator");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String OUTPUT_FOLDER_PATH	= argument[0] + FILE_SEPERATOR;
		String INPUT_CONFIG_PATH = argument[1];
		String INPUT_WORKSPACE_LOC = argument[2];		
		
		/*** Start generating rules*********************************************************************/
		
		SergeService generatingService = ServiceHelper.getService(Activator.getContext(), SergeService.class);
		generatingService.init(HenshinModuleGenerator.class, INPUT_CONFIG_PATH, INPUT_WORKSPACE_LOC, OUTPUT_FOLDER_PATH);
		generatingService.generate(HenshinModuleGenerator.class);
		
				
		return null;
	}

	@Override
	public void stop() {


	}
	

}
