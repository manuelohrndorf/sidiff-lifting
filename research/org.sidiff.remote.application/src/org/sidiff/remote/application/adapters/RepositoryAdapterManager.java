package org.sidiff.remote.application.adapters;

import org.sidiff.common.extension.ExtensionManager;

public class RepositoryAdapterManager extends ExtensionManager<IRepositoryAdapter> {

	RepositoryAdapterManager() {
		super(IRepositoryAdapter.DESCRIPTION);
	}

}
