package org.silift.patching.patch;

import org.sidiff.difference.asymmetric.AsymmetricDifference;

/**
 * Patch class containing all important attributes and files
 * 
 *TODO complete this class
 * 
 * @author dreuling
 *
 */
public class Patch {
	
	private AsymmetricDifference difference;
	
	public AsymmetricDifference getDifference() {
		return difference;
	}

	public void setDifference(AsymmetricDifference difference) {
		this.difference = difference;
	}

	public Patch(AsymmetricDifference difference) {
		this.difference = difference;
	}

}
