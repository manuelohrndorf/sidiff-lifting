package org.sidiff.integration.preferences.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.sidiff.integration.preferences.PreferencesPlugin;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;

/**
 * Contains utility functions for retrieving the global and project specific preference store for the SiDiff settings
 * and for checking whether a project has custom settings attached.
 * @author Robert Müller
 *
 */
public class PreferenceStoreUtil {

	private static ScopedPreferenceStore globalPreferenceStore;
	private static Map<IProject,ScopedPreferenceStore> specificPreferenceStores;

	/**
	 * Returns the preference store for global SiDiff settings.
	 * @return global preference store
	 */
	public static IPreferenceStore getPreferenceStore() {
		if(globalPreferenceStore == null) {
			globalPreferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PreferencesPlugin.PREFERENCE_QUALIFIER);
		}
		return globalPreferenceStore;
	}

	/**
	 * Returns the preference store for SiDiff settings specific to the given project.
	 * @param project the project
	 * @return project specific preference store
	 */
	public static IPreferenceStore getPreferenceStore(IProject project) {
		if(specificPreferenceStores == null) {
			specificPreferenceStores = new HashMap<IProject, ScopedPreferenceStore>();
		}
		ScopedPreferenceStore store = specificPreferenceStores.get(project);
		if(store == null) {
			store = new ScopedPreferenceStore(new ProjectScope(project), PreferencesPlugin.PREFERENCE_QUALIFIER);
			SettingsAdapterUtil.initializeDefaults(store);
			specificPreferenceStores.put(project, store);
		}
		return store;
	}

	private static final QualifiedName KEY_USE_RESOURCE_SETTINGS =
			new QualifiedName(PreferencesPlugin.PREFERENCE_QUALIFIER, "USE_RESOURCE_SETTINGS");

	private static List<ProjectSpecificSettingsListener> projectSpecificListeners =
			new LinkedList<ProjectSpecificSettingsListener>();

	/**
	 * Returns whether the given project has project specific settings attached that should be used.
	 * @param project the project
	 * @return <code>true</code>, if project specific settings should be used, <code>false</code> otherwise
	 * @throws CoreException if retrieving the property failed
	 * @see IProject#getPersistentProperty(QualifiedName)
	 */
	public static boolean useSpecificSettings(IProject project) throws CoreException {
		return Boolean.valueOf(project.getPersistentProperty(KEY_USE_RESOURCE_SETTINGS));
	}

	/**
	 * Enable/disable project specific settings for the given project.
	 * Notifies the {@link ProjectSpecificSettingsListener}s.
	 * @param project the project
	 * @param use whether to enable project specific settings
	 * @throws CoreException if changing the property failed
	 * @see IProject#setPersistentProperty(QualifiedName, String)
	 */
	public static void setUseSpecificSettings(IProject project, boolean use) throws CoreException {
		project.setPersistentProperty(KEY_USE_RESOURCE_SETTINGS, Boolean.toString(use));

		for(ProjectSpecificSettingsListener listener : projectSpecificListeners) {
			listener.useProjectSpecificSettingsChanged(project, use);
		}
	}

	/**
	 * Adds the listener to the list of listeners that will be notified,
	 * when project specific settings are enabled/disabled.
	 * @param listener the listener
	 */
	public static void addProjectSpecificSettingsListener(ProjectSpecificSettingsListener listener) {
		projectSpecificListeners.add(listener);
	}

	/**
	 * Removes the listener from then list of listeners that will be notified,
	 * when the project specific settings are enabled/disabled.
	 * Does nothing, if the listener was not previously added.
	 * @param listener the listener.
	 */
	public static void removeProjectSpecificSettingsListener(ProjectSpecificSettingsListener listener) {
		projectSpecificListeners.remove(listener);
	}
}
