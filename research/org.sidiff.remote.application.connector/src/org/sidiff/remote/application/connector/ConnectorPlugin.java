package org.sidiff.remote.application.connector;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
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
		super.start(context);
		plugin = this;
		
		// get the root of the workspace
		//
		workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(new ConnectedResourceChangeListener());

		/*
		 *  FIXME: Use SiDiffResourceSet and call registerXmiIdResourceExtensions or register the resource factory by
		 *  adding it to resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap() instead of registering it globally.
		 *  Registering the factory globally breaks functionally elsewhere.
		 *  - Robert
		 */
		//TODO add factories in a more generic way
		//Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new UUIDResourceFactoryImpl());
		//FIXME UML should usually use xmi ids, however to force it, we have to extend the UMLResourceFactory
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new UUIDResourceFactoryImpl());
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
