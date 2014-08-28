package org.sidiff.editrule.generator.serge.core;

import java.io.File;
import java.io.FilenameFilter;

import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

public class LogSerializer  {

	/**
	 * FilenameFilter for log files
	 */
	protected FilenameFilter logFilenameFilter = null;
	
	/**
	 * Settings
	 */
	protected SergeSettings settings = null;
	
	/**
	 * Constructor
	 * @param settings
	 */
	public LogSerializer(SergeSettings settings) {
		
		this.settings = settings;
		
		// establishing log file extension filter
		logFilenameFilter = new FilenameFilter() {	
			@Override
			public boolean accept(File f, String fileName) {
				return fileName.endsWith(GlobalConstants.LOG_EXT);
			}
		};
		
		// if deletion of previous logs is wished, do so
		if(settings.isDeleteLogs()) {
			deletePreviousLogs();
		}
		
	}
	
	/**
	 * Deletes previous logs under the general outputfolder.
	 * (Does not cover sub-folders)
	 */
	protected void deletePreviousLogs() {
		
		String outputFolderPath = settings.getOutputFolderPath() + System.getProperty("file.separator") ;
		
		File folder = new File(outputFolderPath);
		if(folder.exists()) {
			File[] files = folder.listFiles(logFilenameFilter);
			for(File file : files) {
				file.delete();
			}
		}
	}
	
}
