package org.sidiff.editrule.fixing;

public class UnsupportedValidationType extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	
	public UnsupportedValidationType(String type){
		this.type = type;
	}
	

	public String getMessage(){
		return "Fixes for the validation type " + type + " are not supported at the moment.";
	}
}
