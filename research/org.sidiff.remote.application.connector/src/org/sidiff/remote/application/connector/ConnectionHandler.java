package org.sidiff.remote.application.connector;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.commands.RequestCommand;
import org.sidiff.remote.common.exceptions.ProtocolHandlerException;

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

	private ConnectionHandler() {
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
		try (Socket server = new Socket(request.getCredentials().getUrl(), request.getCredentials().getPort())) {
			ProtocolHandler protocolHandler = new ProtocolHandler(server);
			protocolHandler.write(request, attachment);
			return protocolHandler.read();
		} catch (IOException | ProtocolHandlerException e) {
			throw new ConnectionException(e);
		}
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
