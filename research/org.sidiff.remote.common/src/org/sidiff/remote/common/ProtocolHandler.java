package org.sidiff.remote.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.exceptions.ProtocolHandlerException;

/**
 * 
 * @author cpietsch
 *
 */
public class ProtocolHandler {

	private Socket socket;
	
	public ProtocolHandler(Socket socket) {
		this.socket = socket;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Command read() throws ProtocolHandlerException {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Command command = (Command) ois.readObject();
			
			if(command.isAttached()) {
				byte[] bytes = new byte[command.getAttachmentSize()];
				ois.read(bytes, 0, bytes.length);
	
				File file = new File(command.getAttachmentName());
				try (FileOutputStream fos = new FileOutputStream(file);
						BufferedOutputStream bos = new BufferedOutputStream(fos)) {
					bos.write(bytes);
					command.setAttachment(file);
				}
				
			}
			return command;
		} catch(IOException | ClassNotFoundException e) {
			throw new ProtocolHandlerException(e);
		}
	}
	
	/**
	 * 
	 * @param command
	 * @param attachment
	 * @throws IOException
	 */
	public void write(Command command, File attachment) throws ProtocolHandlerException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush(); // flush the header
			oos.writeObject(command);
			
			if(attachment != null) {
				try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(attachment))) {
					byte[] bytes = new byte[(int) attachment.length()];
					bis.read(bytes);
					oos.write(bytes);
				}
			}
		} catch(IOException e) {
			throw new ProtocolHandlerException(e);
		}
	}
}
