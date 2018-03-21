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
import java.util.List;
import java.util.Scanner;

import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.remote.common.util.JSONUtil;


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

	private Session session;
	
	private Command command;
	
	private ContentType type;
	
	private Object content;
	
	private String path;
	
	private DateFormat formatter;
	
	private ObjectInputStream ois;
	
	private Scanner scanner;
	
	private ObjectOutputStream oos;
	
	private PrintWriter printWriter;
	
	public ProtocolHandler(String path) {
		this.path = path;
		this.formatter = new SimpleDateFormat("yyyy'-'MM'-'d'_'H'-'m'-'s");
	}

	public void read(InputStream inputStream) throws IOException, ClassNotFoundException {
		
		this.ois = new ObjectInputStream(inputStream);
		this.session = (Session) this.ois.readObject();
		
		this.scanner = new Scanner(this.ois);
		
		this.command = Command.valueOf(this.scanner.nextLine());
			
		String[] metadata = this.scanner.nextLine().split(":");
		
		this.type = ContentType.valueOf(metadata[0]);
		
		if(this.type.equals(ContentType.JSON)||this.type.equals(ContentType.TEXT)) {
			String text = "";
			while(!text.endsWith("#####")) {
				text += scanner.nextLine();
			}
			text = text.substring(0, text.lastIndexOf("#####"));
			if(this.type.equals(ContentType.JSON)) {
				TreeModel treeModel = JSONUtil.convertJson(text);
				content = treeModel;
			}else {
				content = text;
			}
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
		
	}
	

	@SuppressWarnings("unchecked")
	public void write(OutputStream outputStream, Session session, Command command, ContentType type, Object content) throws IOException {
		this.oos = new ObjectOutputStream(outputStream);
		this.oos.writeObject(session);
		
		this.printWriter = new PrintWriter(this.oos, true);
		this.printWriter.println(command);
		this.printWriter.print(type);
		if(type.equals(ContentType.JSON) || type.equals(ContentType.TEXT) || type.equals(ContentType.NONE)) {
			this.printWriter.println();
			if(type.equals(ContentType.JSON)) {
				if(content instanceof List && !((List<?>)content).isEmpty() && ((List<?>)content).get(0) instanceof File) {
					this.printWriter.println(JSONUtil.convertFileList((List<File>) content, session.getSessionID()));
				}else if(content instanceof UUIDResource) {
					this.printWriter.println(JSONUtil.convertEMFResoruce((UUIDResource)content));
				}
			}else if(type.equals(ContentType.TEXT)) {
				this.printWriter.println();
				this.printWriter.println(content);
			}
			this.printWriter.println("#####");
		}else if(type.equals(ContentType.FILE)) {
			
			File file = (File) content;
			int size = (int) file.length();
			this.printWriter.println(':' + file.getName() + ':' + size);
			byte[] bytes = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(bytes);
			outputStream.write(bytes);
			bis.close();
		}		
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
	
	public void close() {
		if(this.scanner != null)
			this.scanner.close();
		if(this.printWriter != null)
			this.printWriter.close();
	}
}
