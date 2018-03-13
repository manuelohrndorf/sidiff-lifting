package org.sidiff.remote.application.connector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.sidiff.remote.common.Command;
import org.sidiff.remote.common.ContentType;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.Session;

public class test {

	public static void main(String[] args) {
		Socket server = null;
		Scanner in = null;
		OutputStream out = null;
		try {
			
		
	   
			server = new Socket( "localhost", 1904 );
			out = server.getOutputStream();
			in = new Scanner(server.getInputStream());
			Session session = new Session("localhost", 1904, "cpietsch");
			File file = new File("D:\\Workspaces\\SiLift\\research\\slicing\\runtime\\client\\sample.ecore");
			ProtocolHandler handler = new ProtocolHandler("");
			handler.write(server.getOutputStream(), session, Command.BROWSE_MODEL, ContentType.FILE, file);
			
//			int size =(int) file.length();
//			out = new ObjectOutputStream(server.getOutputStream());
//			out.writeObject(session);
//			PrintWriter print_out = new PrintWriter(server.getOutputStream(), true);
//			print_out.println(Command.BROWSE);
//			print_out.println(ContentType.FILE + ":" + "sample.ecore" +  ":" + size);
//			
//			  byte[] bytes = new byte[size];
//		      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//		      bis.read(bytes, 0, bytes.length);
//			server.getOutputStream().write(bytes);
//			bis.close();
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		
			try {
				out.close();
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
