package org.sidiff.integration.preferences.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

/**
 * @author rmueller
 */
public abstract class AbstractPreferenceTab implements IPreferenceTab, IExecutableExtension {

	private String helpContextId;

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		helpContextId = config.getAttribute(EXTENSION_POINT_ATTRIBUTE_HELP_CONTEXT_ID);
	}

	@Override
	public String getHelpContextId() {
		return helpContextId;
	}
}
