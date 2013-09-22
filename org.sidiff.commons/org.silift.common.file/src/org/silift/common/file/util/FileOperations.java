package org.silift.common.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.silift.common.exceptions.FileAlreadyExistsException;
import org.silift.common.exceptions.FileNotCreatedException;

public class FileOperations {
	
	public static void createFolder(String path) throws FileNotCreatedException, FileAlreadyExistsException{
		File dir = new File(path);
		if(!dir.exists()){
			if(!dir.mkdir()) throw new FileNotCreatedException("could not create folder!");
		}else{
			throw new FileAlreadyExistsException("folder already exists");
		}
	}
	
	public static void removeFolder(String path){
		File dir = new File(path);
		for(File file : dir.listFiles()){
			if(file.isDirectory())
				removeFolder(file.getPath());
			file.delete();
		}
		dir.delete();
	}
	

	public static void copyFile(String in, String out) throws IOException{
		File inFile = new File(in);
		File outFile = new File(out);
		
		FileChannel inChannel = new FileInputStream(inFile).getChannel();
		FileChannel outChannel = new FileOutputStream(outFile).getChannel();
		
		try{
			inChannel.transferTo(0, inChannel.size(), outChannel);
		}catch(IOException e){
			throw e;
		}finally{
			if(inChannel != null) inChannel.close();
			if(outChannel != null) outChannel.close();
		}
	}
	
	public static void createInfoFile(String path, String info){
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + System.getProperty("file.separator");
		}
		
		try {
			FileWriter file = new FileWriter (path+"patch.info");
			file.write(info);
			file.close();
	      }
	      catch (IOException e) {
	        System.out.println("Fehler: "+e.toString());
	      }
	}
}
