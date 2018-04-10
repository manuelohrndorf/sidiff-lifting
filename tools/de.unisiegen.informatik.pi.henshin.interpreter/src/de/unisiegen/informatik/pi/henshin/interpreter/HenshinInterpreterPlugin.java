package de.unisiegen.informatik.pi.henshin.interpreter;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class HenshinInterpreterPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.unisiegen.informatik.pi.henshin.interpreter"; //$NON-NLS-1$

	// The shared instance
	private static HenshinInterpreterPlugin plugin;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		super.stop(bundleContext);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static HenshinInterpreterPlugin getDefault() {
		return plugin;
	}
}
