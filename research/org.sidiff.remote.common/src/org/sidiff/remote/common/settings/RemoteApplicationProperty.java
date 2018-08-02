package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class RemoteApplicationProperty <T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235122538626024568L;

	private String name;

	private T value;
	
	private List<T> values;
	
	private List<String> documentTypes;

	public RemoteApplicationProperty(String name, T value, List<String> documentTypes) {
		super();
		this.name = name;
		this.value = value;
		this.values = new ArrayList<T>();
		this.documentTypes = documentTypes;
	}
	
	public RemoteApplicationProperty(String name, List<T> values, List<String> documentTypes) {
		this(name, values.get(0), documentTypes);
		this.values.addAll(values);
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}
	
	public List<T> getValues() {
		return values;
	}
	
	public List<String> getDocumentTypes() {
		return documentTypes;
	}	
	
}
