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
import org.osgi.framework.BundleContext;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.remote.application.connector"; //$NON-NLS-1$
		
	private static ConnectorPlugin plugin;
	
	private File session_file;
	
	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		super.start(context);
		plugin = this;
		
		// get the root of the workspace
		//
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String session_path = workspace.getRoot().getLocation().toOSString() + File.separator + Session.SESSION_EXT;
		
		this.session_file = new File(session_path);
		Session session = null;
		if(session_file.exists()) {
			session = readSession(session_file);
		}else {
			session = new Session("localhost", 1904, "cpietsch");
			writeSession(session);
		}
		ConnectionHandler.getInstance().init(session, workspace);
	}

	public void writeSession(Session session) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(session_file));
		oos.writeObject(session);
		oos.close();
	}
	
	/**
	 * reads the {@link ProjectProperties} of a project
	 * 
	 * @param project
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Session readSession(File file) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Session session = (Session)ois.readObject();
		ois.close();
		return session;
	}
	
	public static ConnectorPlugin getInstance() {
		return plugin;
	}
}
