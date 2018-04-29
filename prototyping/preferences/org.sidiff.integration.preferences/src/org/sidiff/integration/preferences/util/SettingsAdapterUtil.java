package org.sidiff.integration.preferences.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.integration.preferences.PreferencesPlugin;
import org.sidiff.integration.preferences.interfaces.ISettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class SettingsAdapterUtil {

	public static List<ISettingsAdapter> getAllAvailableSettingsAdapters() {
		List<ISettingsAdapter> settingsAdapters = new ArrayList<ISettingsAdapter>();
		for(IConfigurationElement element :
				Platform.getExtensionRegistry().getConfigurationElementsFor(ISettingsAdapter.EXTENSION_POINT_ID)) {
			try {
				settingsAdapters.add((ISettingsAdapter)element.createExecutableExtension(ISettingsAdapter.EXTENSION_POINT_ATTRIBUTE));
			} catch(CoreException e) {
				PreferencesPlugin.logWarning("Failed to create ISettingsAdapter contributed by "
											+ element.getDeclaringExtension().getContributor().getName(), e);
			}
		}
		settingsAdapters.sort(ISettingsAdapter.COMPARATOR);
		return settingsAdapters;
	}

	public static void adaptSettingsGlobal(AbstractSettings settings, String documentType) {
		adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(), documentType);
	}

	public static void adaptSettingsProject(AbstractSettings settings, IProject project, String documentType) {
		adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(project), documentType);
	}

	private static void adaptSettings(AbstractSettings settings, IPreferenceStore store, String documentType) {
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			if(adapter.canAdapt(settings)) {
				if(adapter instanceof ISettingsAdapter.DomainSpecific) {
					((ISettingsAdapter.DomainSpecific)adapter).setDocumentType(documentType);
				}
				adapter.load(store);
				adapter.adapt(settings);
			}
		}
	}

	public static void initializeDefaults(IPreferenceStore preferenceStore) {
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			adapter.initializeDefaults(preferenceStore);
		}
	}
}
