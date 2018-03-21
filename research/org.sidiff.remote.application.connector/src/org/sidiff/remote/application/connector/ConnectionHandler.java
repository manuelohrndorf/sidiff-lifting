package org.sidiff.remote.application.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.eclipse.core.resources.IWorkspace;
import org.sidiff.remote.common.Command;
import org.sidiff.remote.common.ContentType;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.Session;

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
	
	
	public Object handleCommand(Command command, Object content) throws IOException, ClassNotFoundException{
		Socket server = null;
		InputStream in = null;
		OutputStream out = null;
		
		Object object = null;
				
		try {
			server = new Socket(this.session.getUrl(), this.session.getPort());
			in = server.getInputStream();
			out = server.getOutputStream();
			switch(command) {
			case BROWSE_MODEL_FILES: 
				this.protocolHandler.write(out, this.session, command, ContentType.NONE, null);
				this.protocolHandler.read(in);
				object = this.protocolHandler.getContent();
				break;
			case BROWSE_MODEL:
				this.protocolHandler.write(out, this.session, command, ContentType.TEXT, content);
				this.protocolHandler.read(in);
				object = this.protocolHandler.getContent();
			default:
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.protocolHandler.close();
			if(server != null) {
				server.close();
			}
		}
		
		return object;
	}
	
	public static ConnectionHandler getInstance() {
		if(ConnectionHandler.connectionHandler == null) {
			ConnectionHandler.connectionHandler = new ConnectionHandler();
		}
		return ConnectionHandler.connectionHandler;
	}
}
