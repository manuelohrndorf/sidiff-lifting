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
		String PACKAGE_LOC = argument[3] + FILE_SEPERATOR;
		
					
		/*** Start generating rules*********************************************************************/
		
		ProfileApplicatorService applicatingService = ServiceHelper.getService(Activator.getContext(), ProfileApplicatorService.class);
		applicatingService.init(ProfileApplicator.class, PACKAGE_LOC, INPUT_CONFIG_PATH, INPUT_FOLDER_PATH, OUTPUT_FOLDER_PATH);
		applicatingService.applyProfile(ProfileApplicator.class);
		
				
		return null;
	}

	@Override
	public void stop() {


	}
	

}
