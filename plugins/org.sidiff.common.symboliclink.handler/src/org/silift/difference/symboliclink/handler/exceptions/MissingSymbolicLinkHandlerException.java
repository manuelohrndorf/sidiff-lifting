package org.silift.difference.symboliclink.handler.exceptions;

public class MissingSymbolicLinkHandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8049930187247460233L;
	
	private String missedSymbolicLinkHandlerKey;
	
	/**
	 * 
	 * @param message
	 */
	public MissingSymbolicLinkHandlerException(String message){
		super(message);
	}
	
	public MissingSymbolicLinkHandlerException(String message, String key){
		super(message);
		missedSymbolicLinkHandlerKey = key;
	}
	
	public String getMissedSymbolicLinkHandlerKey(){
		return missedSymbolicLinkHandlerKey;
	}

}
