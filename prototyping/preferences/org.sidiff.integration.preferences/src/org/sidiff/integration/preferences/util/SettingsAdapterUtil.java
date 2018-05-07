package org.sidiff.integration.preferences.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

	private static List<ISettingsAdapter> settingsAdapters;

	public static List<ISettingsAdapter> getAllAvailableSettingsAdapters() {
		if(settingsAdapters == null) {
			List<IConfigurationElement> elements = new ArrayList<IConfigurationElement>( // copy the list
					Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(ISettingsAdapter.EXTENSION_POINT_ID)));
			// sort the settings adapter extensions according to their pipeline step's position
			elements.sort(new Comparator<IConfigurationElement>() {
				@Override
				public int compare(IConfigurationElement e1, IConfigurationElement e2) {
					String step1 = e1.getAttribute(ISettingsAdapter.EXTENSION_POINT_ATTRIBUTE_PIPELINE_STEP);
					String step2 = e2.getAttribute(ISettingsAdapter.EXTENSION_POINT_ATTRIBUTE_PIPELINE_STEP);
					return PipelineStepUtil.getPipelineStep(step1).getPosition()
							- PipelineStepUtil.getPipelineStep(step2).getPosition();
				}
			});

			settingsAdapters = new ArrayList<ISettingsAdapter>();
			for(IConfigurationElement element : elements) {
				try {
					settingsAdapters.add((ISettingsAdapter)
							element.createExecutableExtension(ISettingsAdapter.EXTENSION_POINT_ATTRIBUTE_CLASS));
				} catch(CoreException e) {
					PreferencesPlugin.logWarning("Failed to create ISettingsAdapter contributed by "
												+ element.getDeclaringExtension().getContributor().getName(), e);
				}
			}
		}
		return settingsAdapters;
	}

	public static void adaptSettingsGlobal(AbstractSettings settings, Set<String> documentTypes) {
		adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(), documentTypes);
	}

	public static void adaptSettingsProject(AbstractSettings settings, IProject project, Set<String> documentTypes) {
		adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(project), documentTypes);
	}

	private static void adaptSettings(AbstractSettings settings, IPreferenceStore store, Set<String> documentTypes) {
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			if(adapter.canAdapt(settings)) {
				adapter.setDocumentTypes(documentTypes);
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
