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
 * Handler for the following protocol:
 * 
 * {@link Session} object
 * {@link Command} 
 * {@link ContentType} : filename? : file-size?
 * file | text
 * 
 * @author cpietsch
 *
 */
public class ProtocolHandler {


	
	private ObjectInputStream ois;
	
	private ObjectOutputStream oos;
	
	public ProtocolHandler(String path) {
//		this.path = path;
//		this.formatter = new SimpleDateFormat("yyyy'-'MM'-'d'_'H'-'m'-'s");
	}

	public Command read(InputStream inputStream) throws IOException, ClassNotFoundException {
		
		this.ois = new ObjectInputStream(inputStream);
		Command request = (Command) this.ois.readObject();
		
		if(request.isAttached()) {
			
			File file = new File(request.getAttachmentName());
			
			
			byte[] bytes = new byte[request.getAttachmentSize()];
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			inputStream.read(bytes, 0, bytes.length);
			bos.write(bytes);
			bos.close();
			request.setAttachment(file);
		}
		return request;
	}
	

	public void write(OutputStream outputStream, Command request, File attachment) throws IOException {
		this.oos = new ObjectOutputStream(outputStream);
		this.oos.writeObject(request);
		
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
