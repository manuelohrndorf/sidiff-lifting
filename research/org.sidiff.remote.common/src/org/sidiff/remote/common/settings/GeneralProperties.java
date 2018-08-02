package org.sidiff.remote.common.settings;

import java.io.Serializable;

public class GeneralProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -543134036135766943L;
	
	private RemoteApplicationProperty<String> scope;
	
	public GeneralProperties(RemoteApplicationProperty<String> scope) {
		super();
		this.scope = scope;
	}



	public RemoteApplicationProperty<String> getScope() {
		return scope;
	}

}
