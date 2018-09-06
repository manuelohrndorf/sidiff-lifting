package org.sidiff.remote.application.connector;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.modelstorage.UUIDResourceFactoryImpl;
import org.sidiff.remote.application.connector.resource.ConnectedResourceChangeListener;
import org.sidiff.remote.common.Credentials;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorPlugin extends Plugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.connector"; //$NON-NLS-1$
		
	/**
	 * The singleton instance
	 */
	private static ConnectorPlugin plugin;
	
	/**
	 * The {@link IWorkspace}
	 */
	private IWorkspace workspace;
	
	/**
	 * The current {@link Credentials}
	 */
	private Credentials credentials;
	
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		super.start(context);
		plugin = this;
		
		// get the root of the workspace
		//
		workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(new ConnectedResourceChangeListener());

		//TODO add factories in a more generic way
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new UUIDResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new UUIDResourceFactoryImpl());
	}
	
	/**
	 * Initializes the current credentials
	 * 
	 * @param url
	 * 			url of the remote application server
	 * @param port
	 * 			port of the remote application server
	 * @param user
	 * 			username for getting access to the remote application server
	 * @param password
	 * 			password for getting access to the remote application server
	 */
	public void init(String url, int port, String user, String password) {
		this.credentials = new Credentials(url, port, user, password);
	}

	/**
	 * 
	 * @return
	 * 		the singleton instance of this plug-in
	 */
	public static ConnectorPlugin getInstance() {
		return plugin;
	}
	
	/**
	 * 
	 * @return the current user {@link Credentials}
	 */
	public Credentials getCredentials() {
		return this.credentials;
	}
}
