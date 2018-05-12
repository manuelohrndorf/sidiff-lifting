package org.sidiff.remote.common.commands;

import java.io.File;

import org.sidiff.remote.common.Credentials;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class RequestCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 469417815359870287L;
	
	/**
	 * 
	 */
	protected Credentials credentials;
	
	/**
	 * 
	 * @param credentials
	 * @param attachment
	 */
	public RequestCommand(Credentials credentials, File attachment) {
		super(attachment);
		this.credentials = credentials;
	}

	/**
	 * 
	 * @return
	 */
	public Credentials getCredentials() {
		return credentials;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(super.toString());
		stringBuilder.append(" (user: " + this.credentials.getUser() + "; session ID: " + this.credentials.getSessionID() + ")\n");
		return stringBuilder.toString();
	}
}
