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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Handler for the following protocol:
 * 
 * {@link Session} object
 * {@link Command} 
 * {@link ContentType} : filename? : file-size?
 * file | text
 * 
 * @author piets
 *
 */
public class ProtocolHandler {

	private Session session;
	
	private Command command;
	
	private ContentType type;
	
	private Object content;
	
	private String path;
	
	private DateFormat formatter;
	
	public ProtocolHandler(String path) {
		this.path = path;
		this.formatter = new SimpleDateFormat("yyyy'-'MM'-'d'_'H'-'m'-'s");
	}

	public void read(InputStream inputStream) throws IOException, ClassNotFoundException {
		
		//we asume a session object
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		this.session = (Session) ois.readObject();
		
		Scanner sc = new Scanner(inputStream);
		
		this.command = Command.valueOf(sc.nextLine());
			
		String[] metadata = sc.nextLine().split(":");
		
		this.type = ContentType.valueOf(metadata[0]);
		
		if(this.type.equals(ContentType.TEXT)) {
			String text = "";
			while(sc.hasNextLine()) {
				text += sc.nextLine();
			}
			
			content = text;
		}else if(this.type.equals(ContentType.FILE)) {

			String name = metadata[1];
			int size = Integer.parseInt(metadata[2]);
			
			
			String temp_folder_path = this.path + File.separator + this.session.getUser() + File.separator
					+ this.session.getSessionID() + File.separator + "temp" + File.separator + this.formatter.format(new Date());
			
			String file_path = temp_folder_path + File.separator + name;
			File file = new File(file_path);
			
			
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			byte[] bytes = new byte[size];
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			inputStream.read(bytes, 0, bytes.length);
			bos.write(bytes);
			bos.close();
			this.content = file;
		}
		sc.close();
	}
	
	public void write(OutputStream outputStream, Session session, Command command, ContentType type, Object content) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		oos.writeObject(session);
		
		PrintWriter pw = new PrintWriter(outputStream, true);
		pw.println(command);
		pw.print(type);
		if(type.equals(ContentType.TEXT)) {
			pw.println();
			pw.println(content);
		}else if(type.equals(ContentType.FILE)) {
			
			File file = (File) content;
			int size = (int) file.length();
			pw.println(':' + file.getName() + ':' + size);
			byte[] bytes = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(bytes);
			outputStream.write(bytes);
			bis.close();
		}
		pw.close();
		
	}

	public Session getSession() {
		return session;
	}

	public Command getCommand() {
		return command;
	}

	public ContentType getType() {
		return type;
	}

	public Object getContent() {
		return content;
	}
}
