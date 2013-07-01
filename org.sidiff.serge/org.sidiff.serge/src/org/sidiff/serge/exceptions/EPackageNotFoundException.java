package org.sidiff.serge.exceptions;

@SuppressWarnings("serial")
public class EPackageNotFoundException extends Exception {

	public EPackageNotFoundException() {
		super("One ore more Epackages could not be found. \n" +
				"Ensure that all plugins containing the Ecore model files are activated in the Run Configuration \n" +
				"and that the correct nsURIs are specified in the SERGe-Configuration.");
	}
}
