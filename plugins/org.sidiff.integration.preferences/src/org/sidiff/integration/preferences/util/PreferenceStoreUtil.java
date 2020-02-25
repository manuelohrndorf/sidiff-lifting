package org.sidiff.integration.preferences.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;

/**
 * Contains utility functions for retrieving the global and project specific preference store for the SiDiff settings
 * and for checking whether a project has custom settings attached.
 * @author rmueller
 */
public class PreferenceStoreUtil {

	/**
	 * Qualifier for global, and project specific preferences
	 */
	public static final String PREFERENCE_QUALIFIER = "org.sidiff.integration.preferences"; //$NON-NLS-1$
	
	private static Map<String,ScopedPreferenceStore> globalPreferenceStore = new HashMap<>();
	private static Map<String,Map<IProject,ScopedPreferenceStore>> specificPreferenceStores = new HashMap<>();

	public static IPreferenceStore getPreferenceStore(String preferenceQualifier) {
		return globalPreferenceStore.computeIfAbsent(preferenceQualifier,
				qualifier -> new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier));
	}
	
	/**
	 * Returns the preference store for global SiDiff settings.
	 * @return global preference store
	 */
	public static IPreferenceStore getPreferenceStore() {
		return getPreferenceStore(PREFERENCE_QUALIFIER);
	}

	/**
	 * Returns the preference store with the given qualifier specific to the given project.
	 * @param project the project
	 * @param preferenceQualifier the preference qualifier
	 * @return project specific preference store
	 */
	public static IPreferenceStore getPreferenceStore(IProject project, String preferenceQualifier) {
		return specificPreferenceStores
			.computeIfAbsent(preferenceQualifier, unused -> new HashMap<>())
			.computeIfAbsent(project, unused -> {
				ScopedPreferenceStore store = new ScopedPreferenceStore(new ProjectScope(project), preferenceQualifier);
				SettingsAdapterUtil.initializeDefaults(store);
				return store;
			});
	}

	private static QualifiedName getKeyUseResourceSettings(String preferenceQualifier) {
		return new QualifiedName(preferenceQualifier, "USE_RESOURCE_SETTINGS");
	}

	private static ListenerList<IProjectSpecificSettingsToggleListener> projectSpecificListeners = new ListenerList<>();

	/**
	 * Returns whether the given project has project specific settings with the
	 * given qualifier attached that should be used.
	 * @param project the project
	 * @param preferenceQualifier the preference qualifier
	 * @return <code>true</code>, if project specific settings should be used, <code>false</code> otherwise
	 * @throws CoreException if retrieving the property failed
	 * @see IProject#getPersistentProperty(QualifiedName)
	 */
	public static boolean useSpecificSettings(IProject project, String preferenceQualifier) throws CoreException {
		return Boolean.valueOf(project.getPersistentProperty(getKeyUseResourceSettings(preferenceQualifier)));
	}

	/**
	 * Enable/disable project specific settings for the given project.
	 * Notifies the {@link ProjectSpecificSettingsListener}s.
	 * @param project the project
	 * @param preferenceQualifier the qualifier for which to toggle project specific settings
	 * @param use whether to enable project specific settings
	 * @throws CoreException if changing the property failed
	 * @see IProject#setPersistentProperty(QualifiedName, String)
	 */
	public static void setUseSpecificSettings(IProject project, String preferenceQualifier, boolean use) throws CoreException {
		project.setPersistentProperty(getKeyUseResourceSettings(preferenceQualifier), Boolean.toString(use));

		for(IProjectSpecificSettingsToggleListener listener : projectSpecificListeners) {
			listener.useProjectSpecificSettingsChanged(project, use);
		}
	}

	/**
	 * Adds the listener to the list of listeners that will be notified,
	 * when project specific settings are enabled/disabled.
	 * @param listener the listener
	 */
	public static void addProjectSpecificSettingsListener(IProjectSpecificSettingsToggleListener listener) {
		projectSpecificListeners.add(listener);
	}

	/**
	 * Removes the listener from then list of listeners that will be notified,
	 * when the project specific settings are enabled/disabled.
	 * Does nothing, if the listener was not previously added.
	 * @param listener the listener.
	 */
	public static void removeProjectSpecificSettingsListener(IProjectSpecificSettingsToggleListener listener) {
		projectSpecificListeners.remove(listener);
	}

	/**
	 * Listener for the activation/deactivation of project specific settings.
	 * @author rmueller
	 */
	public interface IProjectSpecificSettingsToggleListener {
		/**
		 * Called when project specific settings are enabled/disabled for a project.
		 * @param project the project
		 * @param use <code>true</code> if project specific settings were enabled, <code>false</code> otherwise
		 */
		void useProjectSpecificSettingsChanged(IProject project, boolean use);
	}
}
