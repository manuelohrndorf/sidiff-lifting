package org.sidiff.profileapplicator.app;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.services.ServiceHelper;
import org.sidiff.profileapplicator.ProfileApplicatorService;
import org.sidiff.profileapplicator.impl.Activator;
import org.sidiff.profileapplicator.services.ProfileApplicator;

public class Applicator implements IApplication {
    
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		/** File and Folder loading ********************************************************************/
		
    	String FILE_SEPERATOR = System.getProperty("file.separator");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
	
		String INPUT_FOLDER_PATH = argument[0] + FILE_SEPERATOR;	
		String INPUT_CONFIG_PATH = argument[1];
		String OUTPUT_FOLDER_PATH = argument[2] + FILE_SEPERATOR;	
		
		int NUMBER_OF_THREADS = 1;
		
		//optional number of threads
		if(argument.length == 4)
			NUMBER_OF_THREADS = Integer.parseInt(argument[3]);	
		

					
		/*** Start generating rules*********************************************************************/
		
		ProfileApplicatorService applicatingService = ServiceHelper.getService(Activator.getContext(), ProfileApplicatorService.class);
		applicatingService.init(ProfileApplicator.class, INPUT_CONFIG_PATH, INPUT_FOLDER_PATH, OUTPUT_FOLDER_PATH, NUMBER_OF_THREADS);
		applicatingService.applyProfile(ProfileApplicator.class);
		
				
		return null;
	}

	@Override
	public void stop() {


	}
	

}
