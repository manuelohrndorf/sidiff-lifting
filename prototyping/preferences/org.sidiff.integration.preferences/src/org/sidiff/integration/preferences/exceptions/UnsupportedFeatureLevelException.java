/**
 * @author Daniel Roedder
 */
package org.sidiff.integration.preferences.exceptions;

/**
 * 
 * Exception class to indicate that the user did not install the required set of plugins.
 * @author Daniel Roedder
 *
 */
public class UnsupportedFeatureLevelException extends Exception {


	private static final long serialVersionUID = -5174416058072155491L;
	
	/**
	 * String to hold the required feature level for the operation that caused this exception
	 */
	private String featureLevelRequired;

	/**
	 * Constructor for the exception object.
	 * @param featureLevelRequired The required feature level als string, e.g. 'Patching'
	 */
	public UnsupportedFeatureLevelException(String featureLevelRequired) {
		this.featureLevelRequired = featureLevelRequired;
	}
	
	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "The installed feature level does not meet the requirements of the request."
				+ " Minimum level is " + this.featureLevelRequired;
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return getMessage();
	}

	
	
}
