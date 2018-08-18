package org.sidiff.remote.common.settings;

import java.io.Serializable;

public class GeneralProperties implements Serializable {

	public static final String SCOPE = "Scope";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -543134036135766943L;
	
	private SingleSelectionRemoteApplicationProperty<String> scope;
	
	public GeneralProperties(SingleSelectionRemoteApplicationProperty<String> scope) {
		super();
		this.scope = scope;
	}

	public SingleSelectionRemoteApplicationProperty<String> getScope() {
		return scope;
	}

	@Override
	public String toString() {
		return scope.toString();
	}
}
