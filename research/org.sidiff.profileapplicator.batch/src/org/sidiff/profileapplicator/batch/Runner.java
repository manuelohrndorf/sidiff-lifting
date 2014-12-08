package org.sidiff.profileapplicator.batch;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.profileapplicator.ProfileApplicator;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

/**
 * This class should be used if ProfileApplicator is run with OSGi for debug/testing purpose.
 * @author mrindt
 *
 */
public class Runner implements IApplication{


	@Override
	public Object start(IApplicationContext context) throws Exception {

		/** File and Folder loading ********************************************************************/
		
    	String FILE_SEPERATOR = System.getProperty("file.separator");
		String[] argument = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
	
		
		String INPUT_FOLDER_PATH = argument[0] + FILE_SEPERATOR;	
		String INPUT_CONFIG_PATH = argument[1];
		String OUTPUT_FOLDER_PATH = argument[2] + FILE_SEPERATOR;	
		
		ProfileApplicatorSettings settings = new ProfileApplicatorSettings(
				OUTPUT_FOLDER_PATH, INPUT_CONFIG_PATH, INPUT_FOLDER_PATH);
		
		//optional number of threads
		if(argument.length >= 4)
			settings.setNumberOfThreads(Integer.parseInt(argument[3]));	
		
		/*
		//optional use of subfolders
		if(argument.length >= 5)
			settings.setUseSubfolders(Boolean.parseBoolean(argument[4]));
		 */
					
		/*** Start generating rules*********************************************************************/
		
		ProfileApplicator profileApplicator =
				new ProfileApplicator(settings, (IProgressMonitor)null);
		profileApplicator.applyProfile();

		return null;
	}

	@Override
	public void stop() {
		// do nothing

	}
}