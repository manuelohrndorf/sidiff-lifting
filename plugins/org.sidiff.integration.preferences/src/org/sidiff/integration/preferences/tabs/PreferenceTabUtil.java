package org.sidiff.integration.preferences.tabs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.integration.preferences.PreferencesPlugin;

/**
 * Contains utility functions for retrieving {@link IPreferenceTab}s.
 * @author rmueller
 */
public class PreferenceTabUtil {

	/**
	 * Returns all {@link IPreferenceTab}s whose extension specifies the given page and pipeline step.
	 * @param page the page
	 * @param pipelineStepId the pipeline step
	 * @return preference tabs
	 */
	public static List<IPreferenceTab> getPreferenceTabs(String page, String pipelineStepId) {
		List<IPreferenceTab> tabs = new ArrayList<>();
		for(IConfigurationElement element :
			Platform.getExtensionRegistry().getConfigurationElementsFor(IPreferenceTab.EXTENSION_POINT_ID)) {
			if(page.equals(element.getAttribute(IPreferenceTab.EXTENSION_POINT_ATTRIBUTE_PAGE))
					&& pipelineStepId.equals(element.getAttribute(IPreferenceTab.EXTENSION_POINT_ATTRIBUTE_PIPELINE_STEP))) {
				try {
					tabs.add((IPreferenceTab)element.createExecutableExtension(IPreferenceTab.EXTENSION_POINT_ATTRIBUTE_CLASS));
				} catch (CoreException e) {
					PreferencesPlugin.logWarning("Failed to create IPreferenceTab contributed by "
									+ element.getDeclaringExtension().getContributor().getName(), e);
				}
			}
		}
		return tabs;
	}
}
