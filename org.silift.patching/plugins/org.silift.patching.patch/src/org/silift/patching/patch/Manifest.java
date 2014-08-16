package org.silift.patching.patch;

/**
 * 
 * @author cpietsch
 * 
 * This is an object representation of the manifest file
 * of a patch and has to be initialized when a patch file
 * will be loaded.
 */
public class Manifest {
	/**
	 * The name of the "Matcher" used while creating a patch
	 */
	private String matcherName;
	/**
	 * The name of the "SymbolicLinkHandler" used while creating a patch
	 */
	private String symbolicLinkHandlerName;
	
	/**
	 * Getter method.
	 * @return Name of the "Matcher" used while creating a patch
	 */
	public String getMatcherName() {
		return matcherName;
	}
	
	/**
	 * Setter method.
	 * @param matcherName
	 */
	public void setMatcherName(String matcherName) {
		this.matcherName = matcherName;
	}
	
	/**
	 * Getter method.
	 * @return Name of the "SymbolicLinkHandler" used while creating a patch
	 */
	public String getSymbolicLinkHandlerName() {
		return symbolicLinkHandlerName;
	}
	
	/**
	 * Setter method.
	 * @param symbolicLinkHandlerName
	 */
	public void setSymbolicLinkHandlerName(String symbolicLinkHandlerName) {
		this.symbolicLinkHandlerName = symbolicLinkHandlerName;
	}	
	
	//TODO More information should be added here, e.g. the version of the edit rules
	// used while creating a patch
}
