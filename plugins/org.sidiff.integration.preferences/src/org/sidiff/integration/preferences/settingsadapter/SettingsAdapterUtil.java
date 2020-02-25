package org.sidiff.integration.preferences.settingsadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.emf.settings.ISettings;
import org.sidiff.common.emf.settings.ISettingsItem;
import org.sidiff.integration.preferences.PreferencesPlugin;
import org.sidiff.integration.preferences.util.PipelineStepUtil;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * Contains utility functions for retrieving {@link ISettingsAdapter}s, adapting {@link ISettings}
 * and initializing default preference values.
 * @author rmueller
 */
public class SettingsAdapterUtil {

	private static List<ISettingsAdapter> settingsAdapters;

	/**
	 * Returns all available settings adapters ordered according to their pipeline steps.
	 * @return all available settings adapters
	 */
	public static List<ISettingsAdapter> getAllAvailableSettingsAdapters() {
		if(settingsAdapters == null) {
			List<IConfigurationElement> elements = new ArrayList<IConfigurationElement>( // copy the list
					Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(ISettingsAdapter.EXTENSION_POINT_ID)));
			// sort the settings adapter extensions according to their pipeline steps' positions
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

	public static Diagnostic adaptSettingsGlobal(ISettings settings,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings, String preferenceQualifier) {
		return adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(preferenceQualifier),
				documentTypes, consideredSettings);
	}
	
	/**
	 * Adapts the given settings using all available settings adapters that can handle them.
	 * Uses the preferences from the global preference store. 
	 * @param settings the settings to adapt
	 * @param documentTypes the document types that the adapters should use
	 * @param consideredSettings the items of the settings that should be adapted (<code>*SettingsItem</code>),
	 * may be empty to allow all settings to be considered
	 * @return diagnostic containing information about the outcome of the adaptation
	 */
	public static Diagnostic adaptSettingsGlobal(ISettings settings,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings) {
		return adaptSettingsGlobal(settings, documentTypes, consideredSettings, PreferenceStoreUtil.PREFERENCE_QUALIFIER);
	}
	
	public static Diagnostic adaptSettingsProject(ISettings settings, IProject project,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings, String preferenceQualifier) {
		try {
			if(!PreferenceStoreUtil.useSpecificSettings(project, preferenceQualifier)) {
				return new BasicDiagnostic(Diagnostic.ERROR, PreferencesPlugin.PLUGIN_ID, 0,
						"Project specific settings are not enabled for this project.", null);
			}
		} catch (CoreException e) {
			return new BasicDiagnostic(Diagnostic.ERROR, PreferencesPlugin.PLUGIN_ID, 0,
					"Project specific cannot be used for this project.", new Object[] { e });
		}
		return adaptSettings(settings, PreferenceStoreUtil.getPreferenceStore(project, preferenceQualifier),
				documentTypes, consideredSettings);
	}
	
	/**
	 * Adapts the given settings using all available settings adapters that can handle them.
	 * Uses the preferences from the project specific preference store.
	 * @param settings the settings to adapt
	 * @param project the project whose specific preference store should be used
	 * @param documentTypes the document types that the adapters should use
	 * @param consideredSettings the items of the settings that should be adapted (<code>*SettingsItem</code>),
	 * may be empty to allow all settings to be considered
	 * @return diagnostic containing information about the outcome of the adaptation 
	 */
	public static Diagnostic adaptSettingsProject(ISettings settings, IProject project,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings) {
		return adaptSettingsProject(settings, project, documentTypes, consideredSettings, PreferenceStoreUtil.PREFERENCE_QUALIFIER);
	}

	protected static Diagnostic adaptSettings(ISettings settings, IPreferenceStore store,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings) {
		Assert.isNotNull(settings);
		Assert.isNotNull(store);
		Assert.isNotNull(documentTypes);
		Assert.isNotNull(consideredSettings);

		BasicDiagnostic diagnostic = new BasicDiagnostic(PreferencesPlugin.PLUGIN_ID, 0,
				"Settings were validated. See detailed messages below.", null);
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			if(adapter.canAdapt(settings)) {
				adapter.setDiagnosticChain(diagnostic);
				adapter.setDocumentTypes(documentTypes);
				adapter.setConsideredSettings(consideredSettings);
				adapter.load(store);
				adapter.adapt(settings);
			}
		}
		diagnostic.recomputeSeverity();
		return diagnostic;
	}

	public static void initializeDefaults(IPreferenceStore preferenceStore) {
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			adapter.initializeDefaults(preferenceStore);
		}
	}
	

	public static Diagnostic saveSettingsGlobal(ISettings settings, Set<String> documentTypes,
			Set<ISettingsItem> consideredSettings, String preferenceQualifier) {
		return saveSettings(settings, PreferenceStoreUtil.getPreferenceStore(preferenceQualifier), documentTypes, consideredSettings);
	}

	public static Diagnostic saveSettingsProject(ISettings settings, IProject project, boolean enableProjectSpecific,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings, String preferenceQualifier) {
		if(enableProjectSpecific) {
			try {
				PreferenceStoreUtil.setUseSpecificSettings(project, preferenceQualifier, true);
			} catch (CoreException e) {
				return new BasicDiagnostic(Diagnostic.ERROR, PreferencesPlugin.PLUGIN_ID, 0,
						"Project specific cannot be used for this project.", new Object[] { e });
			}
		}
		return saveSettings(settings, PreferenceStoreUtil.getPreferenceStore(project, preferenceQualifier), documentTypes, consideredSettings);
	}
	
	protected static Diagnostic saveSettings(ISettings settings, IPreferenceStore store,
			Set<String> documentTypes, Set<ISettingsItem> consideredSettings) {
		
		BasicDiagnostic diagnostic = new BasicDiagnostic(PreferencesPlugin.PLUGIN_ID, 0,
				"Settings were validated. See detailed messages below.", null);
		for(ISettingsAdapter adapter : getAllAvailableSettingsAdapters()) {
			if(adapter instanceof ISettingsAdapter.Saveable && adapter.canAdapt(settings)) {
				adapter.setDiagnosticChain(diagnostic);
				adapter.setDocumentTypes(documentTypes);
				adapter.setConsideredSettings(consideredSettings);
				ISettingsAdapter.Saveable saveableAdapter = (ISettingsAdapter.Saveable)adapter;
				saveableAdapter.save(store, settings);
			}
		}
		if(store.needsSaving() && store instanceof IPersistentPreferenceStore) {
			try {
				((IPersistentPreferenceStore)store).save();
			} catch (IOException e) {
				diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, PreferencesPlugin.PLUGIN_ID, 0,
						"Failed to persist preference store", new Object[] { e }));
			}
		}
		diagnostic.recomputeSeverity();
		return diagnostic;
	}
}
