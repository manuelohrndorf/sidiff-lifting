package org.sidiff.remote.application.ui.connector;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesConstants;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesPlugin;
import org.sidiff.remote.application.connector.ConnectorPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class ConnectorUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.ui.connector"; //$NON-NLS-1$

	// The shared instance
	private static ConnectorUIPlugin plugin;
	
	/**
	 * The constructor
	 */
	public ConnectorUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		String url = ConnectorPreferencesPlugin.getDefault().getPreferenceStore().getString(ConnectorPreferencesConstants.P_URL);
		int port = ConnectorPreferencesPlugin.getDefault().getPreferenceStore().getInt(ConnectorPreferencesConstants.P_Port);
		String user = ConnectorPreferencesPlugin.getDefault().getPreferenceStore().getString(ConnectorPreferencesConstants.P_USER);
		ConnectorPlugin.getInstance().initSession(url, port, user);
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
	public static ConnectorUIPlugin getDefault() {
		return plugin;
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + path);
	}

}
