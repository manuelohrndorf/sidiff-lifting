package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MultiSelectionRemoteApplicationProperty<T extends Serializable> extends RemoteApplicationProperty<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 674821124782885169L;

	private List<T> values;
	
	public MultiSelectionRemoteApplicationProperty(String name, Map<String,T> items, List<T> values) {
		super(name, items);
		this.values = values;
	}

	public List<T> getValues() {
		return values;
	}

}
