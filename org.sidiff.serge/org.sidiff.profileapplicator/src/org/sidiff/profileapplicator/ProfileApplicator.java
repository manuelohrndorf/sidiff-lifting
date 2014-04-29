package org.sidiff.profileapplicator;

import org.sidiff.profileapplicator.core.Applicator;

public class ProfileApplicator {

	private Applicator applicator;
	
	/**
	 * The constructor.
	 * 
	 * @param pathToConfig
	 * @param pathToInputFolder
	 * @param pathToOutputFolder
	 * @param numberOfThreads
	 */
	public ProfileApplicator(String pathToConfig, String pathToInputFolder, String pathToOutputFolder, int numberOfThreads) {
		
		applicator = new Applicator(pathToConfig, pathToInputFolder, pathToOutputFolder,numberOfThreads);

	}
	
	/**
	 * The method which starts the profile application.
	 */
	public void applyProfile() {
		
		applicator.applyProfile();
		
	}
	
 }
