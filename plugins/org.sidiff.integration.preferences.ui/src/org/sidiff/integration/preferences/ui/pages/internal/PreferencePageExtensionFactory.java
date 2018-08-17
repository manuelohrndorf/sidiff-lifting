package org.sidiff.integration.preferences.ui.pages.internal;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

/**
 * Extension factory for creating preference and property pages using the {@link PreferencePageFactory}.
 * @author Robert Müller
 *
 */
public class PreferencePageExtensionFactory implements IExecutableExtensionFactory, IExecutableExtension {

	private static final String PAGE_KEY = "page";

	private String pageName;

	@SuppressWarnings("unchecked")
	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if(data instanceof String) {
			pageName = (String)data;
		} else if (data instanceof Map<?, ?>) {
			pageName = ((Map<String, String>)data).get(PAGE_KEY);
		}
		if(pageName == null) {
			throw new IllegalArgumentException("page name not specified as string");
		}
	}

	@Override
	public Object create() throws CoreException {
		return PreferencePageFactory.createIndexPage(pageName);
	}
}
