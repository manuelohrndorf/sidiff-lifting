package org.sidiff.integration.preferences.ui.internal;

import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PreferencesUiPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.sidiff.integration.preferences.ui"; //$NON-NLS-1$

	private static PreferencesUiPlugin plugin;

	public static PreferencesUiPlugin getDefault() {
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		PreferencesUiPlugin.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		PreferencesUiPlugin.plugin = null;
		super.stop(bundleContext);
	}

	// logging utility functions
	//

	public static void log(int severity, String message, Throwable throwable) {
		getDefault().getLog().log(new Status(severity, PreferencesUiPlugin.PLUGIN_ID, message, throwable));
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
