package org.silift.common.file.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class Zip {
	/**
	 * 
	 * @param zipFileName
	 * @param path
	 * @param inputFileName
	 */
	public static void zipFiles(String zipFileName, String path, String... inputFileName ){
		BufferedInputStream in = null;
		ZipOutputStream out = null;
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + System.getProperty("file.separator");
		}
		try{
			out = new ZipOutputStream(new FileOutputStream(path + zipFileName+".patch"));
			for(String fileName : inputFileName){
				in = new BufferedInputStream(new FileInputStream(path + fileName));
				int avail = in.available();
				byte[] buffer = new byte[avail];
				out.putNextEntry(new ZipEntry(fileName));
				if(avail > 0){
					in.read(buffer, 0, avail);
					out.write(buffer, 0, buffer.length);
					out.closeEntry();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(in != null) in.close();
				if(out != null) out.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param path
	 * @param fileName
	 */
	public static void extractFiles(String path, String fileName){
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try{
			ZipFile zipFile = new ZipFile(path+fileName);
			Enumeration enu = zipFile.entries();
			while(enu.hasMoreElements()){
				ZipEntry zipEntry = (ZipEntry)enu.nextElement();
				in = new BufferedInputStream(zipFile.getInputStream(zipEntry));
				byte[] buffer;
				int avail = in.available();
				buffer = new byte[avail];
				if(avail > 0){	
					in.read(buffer, 0, avail);
					out = new BufferedOutputStream(new FileOutputStream(path+zipEntry.getName()));
					out.write(buffer, 0, buffer.length);
					out.flush();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(in!=null) in.close();
				if(out!=null) out.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
		
	}
	
	// only for testing purpose
	public static void main(String[] args){
		Zip.extractFiles("D:\\", "create_EReference_x_create_EReference_EMFCompare_lifted_post-processed.patch");
	}
}
