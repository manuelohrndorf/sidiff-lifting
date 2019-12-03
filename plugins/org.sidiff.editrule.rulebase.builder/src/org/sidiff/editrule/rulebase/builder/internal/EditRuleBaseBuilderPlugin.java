package org.sidiff.editrule.rulebase.builder.internal;

import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditRuleBaseBuilderPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String ID = "org.sidiff.editrule.rulebase.builder"; //$NON-NLS-1$

	// The shared instance
	private static EditRuleBaseBuilderPlugin plugin;
	
	/**
	 * The constructor
	 */
	public EditRuleBaseBuilderPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EditRuleBaseBuilderPlugin getDefault() {
		return plugin;
	}

	// logging utility functions
	//

	public static void log(int severity, String message, Throwable throwable) {
		getDefault().getLog().log(new Status(severity, ID, message, throwable));
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
