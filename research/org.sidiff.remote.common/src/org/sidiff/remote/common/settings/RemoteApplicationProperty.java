package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class RemoteApplicationProperty<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235122538626024568L;

	private String name;
	
	private Map<String,T> items;
	

	private String documentType = "generic";

	public RemoteApplicationProperty(String name, Map<String,T> items) {
		this.name = name;
		this.items = items;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String,T>  getItems() {
		return items;
	};
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
