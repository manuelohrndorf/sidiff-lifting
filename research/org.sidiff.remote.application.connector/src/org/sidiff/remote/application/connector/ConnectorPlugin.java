package org.sidiff.remote.application.connector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.framework.BundleContext;
import org.sidiff.common.emf.modelstorage.UUIDResourceFactoryImpl;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.resource.ConnectedResourceChangeListener;
import org.sidiff.remote.application.connector.session.Session;
import org.sidiff.remote.common.Credentials;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorPlugin extends Plugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.connector"; //$NON-NLS-1$
	
	private static final String META_FOLDER = ".sidiff";
	
	/**
	 * The singleton instance
	 */
	private static ConnectorPlugin plugin;
	
	/**
	 * The serialization of {@link #session}
	 */
	private File session_file;
	
	/**
	 * The {@link IWorkspace}
	 */
	private IWorkspace workspace;
	
	/**
	 * The current {@link Session}
	 */
	private Session session;
	
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
		String session_path = workspace.getRoot().getLocation().toOSString() + File.separator +  META_FOLDER + File.separator + Session.SESSION_EXT;
		this.session_file = new File(session_path);
		//TODO add factories in a more generic way
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new UUIDResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new UUIDResourceFactoryImpl());
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
				if(!this.session_file.exists()) {
					this.session_file.getParentFile().mkdirs();
				}
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
	
//	private void createFolder(File file) {
//		if(file.isDirectory())
//			if(!file.getParentFile().exists()) {
//				createFolder(file.getParentFile());
//			}
//		file.mkdir();
//		
//	}
	
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
	
	public Credentials getCredentials() {
		return this.credentials;
	}
	
	public void init(String url, int port, String user, String password) {
		try {
			this.session = readSession();
		}catch (InvalidSessionException e) {
			this.session = new Session();
		}
		this.credentials = new Credentials(url, port, this.session.getSessionID(), user, password);
	}
}
