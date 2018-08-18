package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.Map;

public class SingleSelectionRemoteApplicationProperty<T extends Serializable> extends RemoteApplicationProperty<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 674821124782885169L;
	

	private T value;
	
	public SingleSelectionRemoteApplicationProperty(String name, Map<String,T> items, T value) {
		super(name, items);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getName() + "[" + getDocumentType() + "]=" + value;
	}
}
