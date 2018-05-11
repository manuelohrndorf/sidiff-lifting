package org.sidiff.remote.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.sidiff.remote.common.commands.Command;


/**
 * 
 * @author cpietsch
 *
 */
public class ProtocolHandler {
	
	/**
	 * 
	 */
	private ObjectInputStream ois;
	
	/**
	 * 
	 */
	private ObjectOutputStream oos;
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Command read(InputStream inputStream) throws IOException, ClassNotFoundException {
		
		this.ois = new ObjectInputStream(inputStream);
		Command command = (Command) this.ois.readObject();
		
		if(command.isAttached()) {
			
			File file = new File(command.getAttachmentName());
			
			
			byte[] bytes = new byte[command.getAttachmentSize()];
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			inputStream.read(bytes, 0, bytes.length);
			bos.write(bytes);
			bos.close();
			command.setAttachment(file);
		}
		return command;
	}
	
	/**
	 * 
	 * @param outputStream
	 * @param command
	 * @param attachment
	 * @throws IOException
	 */
	public void write(OutputStream outputStream, Command command, File attachment) throws IOException {
		this.oos = new ObjectOutputStream(outputStream);
		this.oos.writeObject(command);
		
		if(attachment != null) {
			int size = (int) attachment.length();
			byte[] bytes = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(attachment));
			bis.read(bytes);
			outputStream.write(bytes);
			bis.close();
		}		
	}
}
