package org.sidiff.difference.lifting.edit2recognition.exceptions;


/**
 * Base exception of all edit-rule to recognition-rule transformation exceptions.
 */
public class EditToRecognitionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new exception with the specified detail message. The cause is not initialized,
	 * and may subsequently be initialized by a call to initCause.
	 * 
	 * @param message
	 *            The detail message. The detail message is saved for later retrieval by the
	 *            getMessage() method.
	 */
	public EditToRecognitionException(String message) {
		super(message);
	}
	
}
