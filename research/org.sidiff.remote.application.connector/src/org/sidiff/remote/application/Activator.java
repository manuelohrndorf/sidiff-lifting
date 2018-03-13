package org.sidiff.remote.application;

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
import org.sidiff.remote.application.connector.ConnectionHandler;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class Activator extends Plugin {

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		super.start(context);
		
		// get the root of the workspace
		//
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String session_path = workspace.getRoot().getLocation().toOSString() + File.separator + Session.SESSION_EXT;
		
		File session_file = new File(session_path);
		Session session = null;
		if(session_file.exists()) {
			session = readSession(session_file);
		}else {
			session = new Session("localhost", 1904, "cpietsch");
			writeSession(session_file, session);
		}
		ConnectionHandler.getInstance().init(session, workspace);
	}

	private static void writeSession(File file, Session session) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
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
	public static Session readSession(File file) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		Session session = (Session)ois.readObject();
		ois.close();
		return session;
	}
	
}
