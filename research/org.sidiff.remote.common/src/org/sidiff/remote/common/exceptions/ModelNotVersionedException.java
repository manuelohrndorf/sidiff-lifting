package org.sidiff.remote.common.exceptions;

/**
 * 
 * @author cpietsch
 *
 */
public class ModelNotVersionedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5764434042600214817L;

	/**
	 * 
	 * @param local_model_path
	 * 				project relative model path
	 */
	public ModelNotVersionedException(String local_model_path) {
		super(local_model_path + " is not versioned!");
	}
}
