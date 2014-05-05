package org.sidiff.profileapplicator;

import org.sidiff.profileapplicator.core.Applicator;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;

public class ProfileApplicator {

	private Applicator applicator;
	
	/**
	 * The constructor.
	 * 
	 * @param settings ProfileApplicatorSettings 
	 */
	public ProfileApplicator(ProfileApplicatorSettings settings) {
		
		applicator = new Applicator(settings);

	}
	
	/**
	 * The method which starts the profile application.
	 */
	public void applyProfile() {
		
		applicator.applyProfile();
		
	}
	
 }
