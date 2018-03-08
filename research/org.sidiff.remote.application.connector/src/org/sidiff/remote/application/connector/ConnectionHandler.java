package org.sidiff.remote.application.connector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.SequenceInputStream;
import java.net.Socket;
import java.util.Scanner;

import org.sidiff.remote.common.Session;

public class ConnectionHandler {

	private static ConnectionHandler connectionHandler;
	
	private Session session;
	
	private ConnectionHandler() {
		
	}
	
	public void init(Session session) {
		this.session = session;
	}
	
	
	public Object handleCommand(String command){
		Socket server = null;
		try {
			
			server = new Socket(session.getUrl(), session.getPort());

			PrintWriter print_out = new PrintWriter(server.getOutputStream());
			print_out.write(command);
			print_out.close();
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			out.writeObject(session);

			Scanner in = new Scanner(server.getInputStream());
		
		} catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return command;
	}
	
	public static ConnectionHandler getInstance() {
		if(ConnectionHandler.connectionHandler == null) {
			ConnectionHandler.connectionHandler = new ConnectionHandler();
		}
		return ConnectionHandler.connectionHandler;
	}
}
