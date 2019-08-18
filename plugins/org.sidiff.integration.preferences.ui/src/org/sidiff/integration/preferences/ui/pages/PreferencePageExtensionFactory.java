package org.sidiff.integration.preferences.ui.pages;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.sidiff.integration.preferences.ui.pages.internal.PreferencePageFactory;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * Extension factory for creating preference and property pages using the {@link PreferencePageFactory}.
 * @author Robert Müller
 *
 */
public class PreferencePageExtensionFactory implements IExecutableExtensionFactory, IExecutableExtension {

	private static final String PAGE_KEY = "page";
	private static final String PREFERENCE_QUALIFIER_KEY = "preferenceStore";

	private String pageName;
	private String preferenceQualifier;

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if(data instanceof String) {
			String[] stringData = ((String)data).split(",");
			if(stringData.length > 0) {
				pageName = stringData[0];
			}
			if(stringData.length > 1) {
				preferenceQualifier = stringData[1];
			}
		} else if (data instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			Map<String, String> xmlData = (Map<String, String>)data;
			pageName = xmlData.get(PAGE_KEY);
			preferenceQualifier = xmlData.get(PREFERENCE_QUALIFIER_KEY);
		}
		if(pageName == null) {
			throw new IllegalArgumentException("page name not specified as string");
		}
		if(preferenceQualifier == null) {
			preferenceQualifier = PreferenceStoreUtil.PREFERENCE_QUALIFIER;
		}
	}

	@Override
	public Object create() throws CoreException {
		return PreferencePageFactory.createIndexPage(pageName, preferenceQualifier);
	}
}
