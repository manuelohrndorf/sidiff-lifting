package org.sidiff.integration.preferences.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.sidiff.integration.preferences.PreferencesPlugin;

/**
 * Contains utility functions for retrieving the global and project specific preference store for the SiDiff settings
 * and for checking whether a project has custom settings attached.
 * @author Robert Müller
 *
 */
public class PreferenceStoreUtil {

	/**
	 * Returns the preference store for global SiDiff settings.
	 * @return global preference store
	 */
	public static IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, PreferencesPlugin.PREFERENCE_QUALIFIER);
	}

	/**
	 * Returns the preference store for SiDiff settings specific to the given project.
	 * @param project the project
	 * @return project specific preference store
	 */
	public static IPreferenceStore getPreferenceStore(IProject project) {
		ScopedPreferenceStore store = new ScopedPreferenceStore(new ProjectScope(project), PreferencesPlugin.PREFERENCE_QUALIFIER);
		SettingsAdapterUtil.initializeDefaults(store);
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
	 */
	public static boolean useSpecificSettings(IProject project) {
		try {
			return Boolean.valueOf(project.getPersistentProperty(KEY_USE_RESOURCE_SETTINGS));
		} catch(CoreException e) {
			return false;
		}
	}

	/**
	 * Enable/disable project specific settings for the given project.
	 * Notifies the {@link ProjectSpecificSettingsListener}s.
	 * @param project the project
	 * @param use whether to enable project specific settings
	 * @throws CoreException changing property failed, see {@link IProject#setPersistentProperty(QualifiedName, String)}
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
