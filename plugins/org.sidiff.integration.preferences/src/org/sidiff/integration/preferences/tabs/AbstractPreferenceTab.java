package org.sidiff.integration.preferences.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

public abstract class AbstractPreferenceTab implements IPreferenceTab, IExecutableExtension {

	private String helpContextId;

	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		helpContextId = config.getAttribute(EXTENSION_POINT_ATTRIBUTE_HELP_CONTEXT_ID);
	}

	@Override
	public String getHelpContextId() {
		return helpContextId;
	}
}
