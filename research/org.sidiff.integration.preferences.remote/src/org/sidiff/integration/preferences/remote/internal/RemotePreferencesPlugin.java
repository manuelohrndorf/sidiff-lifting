package org.sidiff.integration.preferences.remote.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class RemotePreferencesPlugin implements BundleActivator {

	public static final String PLUGIN_ID = "org.sidiff.integration.preferences.remote";

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		RemotePreferencesPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		RemotePreferencesPlugin.context = null;
	}

}
