package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.application.exception.UnsupportedProtocolException;
import org.sidiff.remote.common.Command;
import org.sidiff.remote.common.ContentType;
import org.sidiff.remote.common.ProtocolHandler;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplicationServer implements IApplication {
		
	private ServerSocket server;
	
	private Map<String, SiDiffRemoteApplication> client_sessions;
	
	private IWorkspace workspace;
	
	private ProtocolHandler protocolHandler;

	@Override
	public Object start(IApplicationContext context) throws IOException {
		
		System.out.println("/ Sidiff Remote Application is starting");
				
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String arg_port = args[0];
		
		Integer port = arg_port == null || arg_port.isEmpty() ? 2345 : Integer.parseInt(arg_port);
		
		this.server = new ServerSocket(port);
		
		this.client_sessions = new HashMap<String, SiDiffRemoteApplication>();
		
		this.workspace = ResourcesPlugin.getWorkspace();
		
		this.protocolHandler = new ProtocolHandler(this.workspace.getRoot().getLocation().toOSString());
		System.out.println("port: " + port);
		System.out.println("workspace: " + workspace.getRoot().getLocation().toOSString());
		System.out.println("Sidiff Remote Application is running");
		
		while(true) {
			System.out.println("waiting for request");
			Socket client = server.accept();
			try {
				System.out.println("processing request");
				handleRequest(client);
			} catch (CoreException | UnsupportedProtocolException | IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				this.protocolHandler.close();
				client.close();
			}
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	
	private void handleRequest(Socket client) throws CoreException, UnsupportedProtocolException, IOException, ClassNotFoundException  {
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
		
		this.protocolHandler.read(in);
		
		SiDiffRemoteApplication app = client_sessions.get(this.protocolHandler.getSession().getSessionID());
		if(app == null) {
			app = new SiDiffRemoteApplication(this.workspace, this.protocolHandler.getSession());
			client_sessions.put(this.protocolHandler.getSession().getSessionID(), app);
			
		}
		System.out.println("client session:" + this.protocolHandler.getSession().getSessionID());
		
		switch(this.protocolHandler.getCommand()) {
		case BROWSE_MODEL_FILES:
			List<File> files = app.browseModelFiles();
			
			this.protocolHandler.write(out, app.getSession(), Command.BROWSE_MODEL_FILES, ContentType.JSON, files);
			break;
			
		case BROWSE_MODEL:
			UUIDResource resource = app.browseModel(this.protocolHandler.getContent().toString());
			this.protocolHandler.write(out, app.getSession(), Command.BROWSE_MODEL, ContentType.JSON, resource);
		default:
		}
	}

}
