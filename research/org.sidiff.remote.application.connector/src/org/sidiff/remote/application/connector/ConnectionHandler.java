package org.sidiff.remote.application.connector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.commands.RequestCommand;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectionHandler {

	/**
	 * The singleton instance
	 */
	private static ConnectionHandler connectionHandler;
	
	/**
	 * The {@link ProtocolHandler}
	 */
	private ProtocolHandler protocolHandler;
	
	private ConnectionHandler() {
		this.protocolHandler = new ProtocolHandler();
	}	
	
	/**
	 * handles a given {@link Command}
	 * 
	 * @param request
	 * @param attachment
	 * @return
	 * @throws ConnectionException
	 */
	public Command handleRequest(RequestCommand request, File attachment) throws ConnectionException {
		Socket server = null;
		InputStream in = null;
		OutputStream out = null;
		
		Command reply = null;
				
		try {
			server = new Socket(request.getCredentials().getUrl(), request.getCredentials().getPort());
			in = server.getInputStream();
			out = server.getOutputStream();
			
			this.protocolHandler.write(out, request, attachment);
			reply = this.protocolHandler.read(in);
				
		} catch (IOException | ClassNotFoundException e) {
			throw new ConnectionException(e);
		}finally {
			if(server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return reply;
	}
	
	/**
	 * gets the singleton instance
	 * 
	 * @return
	 */
	public static ConnectionHandler getInstance() {
		if(ConnectionHandler.connectionHandler == null) {
			ConnectionHandler.connectionHandler = new ConnectionHandler();
		}
		return ConnectionHandler.connectionHandler;
	}
}
