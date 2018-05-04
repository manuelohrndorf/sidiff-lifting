package org.sidiff.remote.application.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.sidiff.remote.application.connector.resource.ConnectedResourceChangeListener;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.exceptions.InvalidSessionException;

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
	 * The serialization of {@link #session}
	 */
	private File session_file;
	
	/**
	 * The current {@link Session}
	 */
	private Session session;
	
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		super.start(context);
		plugin = this;
		
		// get the root of the workspace
		//
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(new ConnectedResourceChangeListener());
		String session_path = workspace.getRoot().getLocation().toOSString() + File.separator + Session.SESSION_EXT;
		
		this.session_file = new File(session_path);
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
	 * write the current {@link Session}
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidSessionException
	 */
	public void writeSession() throws InvalidSessionException {
		if(this.session != null) {			
			try {
				FileOutputStream fos = new FileOutputStream(this.session_file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(this.session);
				oos.close();
			}catch(IOException e) {
				throw new InvalidSessionException(e);
			}
		
		}else {
			throw new InvalidSessionException();
		}
		
	}
	
	/**
	 * get the current {@link Session}
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidSessionException 
	 */
	public Session readSession() throws InvalidSessionException {
		if(this.session == null) {
			try {
				FileInputStream fis = new FileInputStream(this.session_file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				this.session = (Session)ois.readObject();
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				throw new InvalidSessionException(e);
			}				
		}
		
		return session;
	}	
	
	public void initSession(String url, int port, String user) {
		this.session = new Session(url, port, user);
	}
}
