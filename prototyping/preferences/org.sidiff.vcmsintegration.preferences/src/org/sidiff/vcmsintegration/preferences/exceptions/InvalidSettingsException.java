/**
 * @author Daniel Roedder
 */
package org.sidiff.vcmsintegration.preferences.exceptions;

/**
 * 
 * Exception class to indicate that the settings set by the user are not valid.
 * @author Daniel Roedder
 */
public class InvalidSettingsException extends Exception {

	private static final long serialVersionUID = -9028405255258581356L;

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "The settings contain errors. Please check and try again";
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return getMessage();
	}

	
	
}
