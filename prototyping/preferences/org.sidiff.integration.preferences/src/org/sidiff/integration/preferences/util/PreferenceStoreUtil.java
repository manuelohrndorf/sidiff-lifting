package org.sidiff.integration.preferences.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.sidiff.integration.preferences.Activator;
import org.sidiff.integration.preferences.SiDiffPreferences;

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
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID);
	}

	/**
	 * Returns the preference store for SiDiff settings specific to the given project.
	 * @param project the project
	 * @return project specific preference store
	 */
	public static IPreferenceStore getPreferenceStore(IProject project) {
		ScopedPreferenceStore store = new ScopedPreferenceStore(new ProjectScope(project), Activator.PLUGIN_ID);
		store.setSearchContexts(new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE });
		return store;
	}

	private static final QualifiedName KEY_USE_RESOURCE_SETTINGS =
			new QualifiedName(SiDiffPreferences.QUALIFIER, "USE_RESOURCE_SETTINGS");

	/**
	 * Returns whether the given project has project specific settings attached.
	 * @param project the project
	 * @return <code>true</code>, if the project has project specific settings, <code>false</code> otherwise
	 */
	public static boolean hasSpecificSettings(IProject project) {
		try {
			return Boolean.valueOf(project.getPersistentProperty(KEY_USE_RESOURCE_SETTINGS));
		} catch(CoreException e) {
			return false;
		}
	}

	/**
	 * Sets the flag of the given project to enable project specific settings.
	 * @param project the project
	 * @param use whether to enable project specific settings
	 * @throws CoreException if the setting of the flag failed
	 */
	public static void setUseSpecificSettings(IProject project, boolean use) throws CoreException {
		project.setPersistentProperty(KEY_USE_RESOURCE_SETTINGS, Boolean.toString(use));
	}
}
