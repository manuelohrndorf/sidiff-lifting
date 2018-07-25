package de.unisiegen.informatik.pi.henshin.interpreter.exceptions;

public class UnresolvedArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6957599287963537688L;
	
	public UnresolvedArgumentException(String name) {
		super("No value for parameter " + name);
	}
}
