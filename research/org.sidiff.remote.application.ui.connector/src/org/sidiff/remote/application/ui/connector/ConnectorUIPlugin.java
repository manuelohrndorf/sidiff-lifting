package org.sidiff.remote.application.ui.connector;

import java.io.IOException;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesConstants;
import org.sidiff.integration.preferences.connector.ConnectorPreferencesPlugin;
import org.sidiff.integration.preferences.connector.ConnectorPropertyChangeListener;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.ConnectorPlugin;
import org.sidiff.remote.application.ui.connector.console.SiDiffClientConnectorConsole;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author cpietsch
 */
public class ConnectorUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.ui.connector"; //$NON-NLS-1$

	// The shared instance
	private static ConnectorUIPlugin plugin;
	
	private static SiDiffClientConnectorConsole console;
	
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
		
		initConsole();
		
		IPreferenceStore store = ConnectorPreferencesPlugin.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				switch(event.getProperty()) {
				case ConnectorPreferencesConstants.P_URL:
					break;
				case ConnectorPreferencesConstants.P_Port:
					break;
				case ConnectorPreferencesConstants.P_USER:
					break;
				case ConnectorPreferencesConstants.P_PASSWORD:
					break;
				default:
				}
				
			}
		});
		
		String url = store.getString(ConnectorPreferencesConstants.P_URL);
		int port = store.getInt(ConnectorPreferencesConstants.P_Port);
		String user = store.getString(ConnectorPreferencesConstants.P_USER);
		String password = store.getString(ConnectorPreferencesConstants.P_PASSWORD);
		ConnectorPlugin.getInstance().init(url, port, user, password);
		
		store.addPropertyChangeListener(new ConnectorPropertyChangeListener(ConnectorPlugin.getInstance().getCredentials()));
		
		printMessage(ConnectorFacade.getSession().toString());
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
	
	public static void initConsole() {
		if(ConnectorUIPlugin.console == null) {
			IConsoleManager consoleManager = ConsolePlugin.getDefault().getConsoleManager();
			for(IConsole console : consoleManager.getConsoles()) {
				if(console.getName().equals(SiDiffClientConnectorConsole.CONSOLE_NAME)) {
					ConnectorUIPlugin.console = (SiDiffClientConnectorConsole) console;
					return;
				}
			}
			ConnectorUIPlugin.console = new SiDiffClientConnectorConsole();
		}
	}
	
	public static void printMessage(String msg) {
		IOConsoleOutputStream out = console.newOutputStream();
		out.setActivateOnWrite(true);
		try {
			out.write(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + path);
	}

}
