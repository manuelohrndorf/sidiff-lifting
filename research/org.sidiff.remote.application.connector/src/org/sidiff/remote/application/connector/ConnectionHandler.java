package org.sidiff.remote.application.connector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.eclipse.core.resources.IWorkspace;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.commands.Command;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectionHandler {

	private static ConnectionHandler connectionHandler;
	
	private IWorkspace workspace;
	
	private Session session;
	
	private ProtocolHandler protocolHandler;
	
	private ConnectionHandler() {
		
	}
	
	public void init(Session session, IWorkspace workspace) {
		this.workspace = workspace;
		this.session = session;
		this.protocolHandler = new ProtocolHandler(this.workspace.getRoot().getLocation().toOSString());
	}
	
	
	public Command handleRequest(Command request, File attachment) throws IOException, ClassNotFoundException{
		Socket server = null;
		InputStream in = null;
		OutputStream out = null;
		
		Command reply = null;
				
		try {
			server = new Socket(this.session.getUrl(), this.session.getPort());
			in = server.getInputStream();
			out = server.getOutputStream();
			
			this.protocolHandler.write(out, request, attachment);
			reply = this.protocolHandler.read(in);
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(server != null) {
				server.close();
			}
		}
		
		return reply;
	}
	
	public static ConnectionHandler getInstance() {
		if(ConnectionHandler.connectionHandler == null) {
			ConnectionHandler.connectionHandler = new ConnectionHandler();
		}
		return ConnectionHandler.connectionHandler;
	}
	
	public Session getSession() {
		return session;
	}
}
