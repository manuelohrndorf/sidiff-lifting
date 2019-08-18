package org.sidiff.integration.preferences;

import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The PreferencesPlugin class controls the plug-in life cycle
 * and contains some utility functions for logging.
 */
public class PreferencesPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.integration.preferences"; //$NON-NLS-1$

	// The shared instance
	private static PreferencesPlugin plugin;
	
	/**
	 * The constructor
	 */
	public PreferencesPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PreferencesPlugin getDefault() {
		return plugin;
	}

	// logging utility functions
	//

	public static void log(int severity, String message, Throwable throwable) {
		getDefault().getLog().log(new Status(severity, PreferencesPlugin.PLUGIN_ID, message, throwable));
	}

	public static void logError(String message, Throwable throwable) {
		log(Status.ERROR, message, throwable);
	}
	
	public static void logWarning(String message, Throwable throwable) {
		log(Status.WARNING, message, throwable);
	}
	
	public static void logWarning(String message) {
		logWarning(message, null);
	}
}
