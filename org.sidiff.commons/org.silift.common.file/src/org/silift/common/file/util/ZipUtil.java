package org.silift.common.file.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

	String dirToZip;
	String zipFile;

	public void zip(String in, String out){
		try {
			this.dirToZip = in;
			this.zipFile = out;
			File file = new File(zipFile + ".zip");
			ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
			zipDir(zipFile, dirToZip, new File(dirToZip), zipOutputStream);
			zipOutputStream.close();
		}catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	 private void zipDir(String zipFile, String dirToZip, File dirToZipFile, ZipOutputStream zipOutputStream) {
		 if (zipFile == null || dirToZip == null || dirToZipFile == null || zipOutputStream == null || !dirToZipFile.isDirectory()) return;

		 FileInputStream fileInputStream = null;
		 try {
			 File[] fileArray = dirToZipFile.listFiles();
			 String path;
			 for (File file : fileArray) {
				 if (file.isDirectory()) {
					 zipDir(zipFile, dirToZip, file, zipOutputStream);
					 continue;
				 }
				 fileInputStream = new FileInputStream(file);
				 path = file.getCanonicalPath();
				 String name = path.substring(dirToZip.length(), path.length());
				 System.out.println("zip " + name);
				 zipOutputStream.putNextEntry(new ZipEntry(name));
				 int len;
				 byte[] buffer = new byte[2048];
				 while ((len = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
					 zipOutputStream.write(buffer, 0, len);
				 }
				 fileInputStream.close();
			 }
		 }catch (FileNotFoundException e) {
			 e.printStackTrace();
		 }catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 

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
}
