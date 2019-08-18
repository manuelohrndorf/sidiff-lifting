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

	public static IPreferenceStore getPreferenceStore(IProject project, String preferenceQualifier) {
		return specificPreferenceStores
			.computeIfAbsent(preferenceQualifier, unused -> new HashMap<>())
			.computeIfAbsent(project, unused -> {
				ScopedPreferenceStore store = new ScopedPreferenceStore(new ProjectScope(project), preferenceQualifier);
				SettingsAdapterUtil.initializeDefaults(store);
				return store;
			});
	}
	
	/**
	 * Returns the preference store for SiDiff settings specific to the given project.
	 * @param project the project
	 * @return project specific preference store
	 */
	public static IPreferenceStore getPreferenceStore(IProject project) {
		return getPreferenceStore(project, PREFERENCE_QUALIFIER);
	}

	private static QualifiedName getKeyUseResourceSettings(String preferenceQualifier) {
		return new QualifiedName(preferenceQualifier, "USE_RESOURCE_SETTINGS");
	}

	private static List<ProjectSpecificSettingsListener> projectSpecificListeners =
			new LinkedList<ProjectSpecificSettingsListener>();

	public static boolean useSpecificSettings(IProject project, String preferenceQualifier) throws CoreException {
		return Boolean.valueOf(project.getPersistentProperty(getKeyUseResourceSettings(preferenceQualifier)));
	}
	
	/**
	 * Returns whether the given project has project specific settings attached that should be used.
	 * @param project the project
	 * @return <code>true</code>, if project specific settings should be used, <code>false</code> otherwise
	 * @throws CoreException if retrieving the property failed
	 * @see IProject#getPersistentProperty(QualifiedName)
	 */
	public static boolean useSpecificSettings(IProject project) throws CoreException {
		return useSpecificSettings(project, PREFERENCE_QUALIFIER);
	}

	public static void setUseSpecificSettings(IProject project, String preferenceQualifier, boolean use) throws CoreException {
		project.setPersistentProperty(getKeyUseResourceSettings(preferenceQualifier), Boolean.toString(use));

		for(ProjectSpecificSettingsListener listener : projectSpecificListeners) {
			listener.useProjectSpecificSettingsChanged(project, use);
		}
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
		project.setPersistentProperty(getKeyUseResourceSettings(PREFERENCE_QUALIFIER), Boolean.toString(use));

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
