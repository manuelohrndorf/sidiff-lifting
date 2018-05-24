package org.sidiff.integration.preferences.util;

import org.eclipse.core.resources.IProject;

/**
 * Listener for the activation/deactivation of project specific settings.
 * @author Robert Müller
 *
 */
public interface ProjectSpecificSettingsListener {
	/**
	 * Called when project specific settings are enabled/disabled for a project.
	 * @param project the project
	 * @param use <code>true</code> if project specific settings were enabled, <code>false</code> otherwise
	 */
	void useProjectSpecificSettingsChanged(IProject project, boolean use);
}
