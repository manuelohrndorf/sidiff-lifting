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

import org.silift.common.exceptions.FileAlreadyExistsException;
import org.silift.common.exceptions.FileNotCreatedException;


public class ZipUtil {

	String dirToZip;
	String zipFile;

	/**
	 * Zips a directory given by an absolute path.
	 * 
	 * <p><strong>Example:</strong> <code>zip("/example/folderToZip", "/example/zipFile")</code></p>
	 * <p>The directory <code>folderToZip</code> in the directory example will be saved as <code>zipFile.zip</code> in the same directory.</p>
	 * 
	 * @param inputPath absolute path of the directory to zip
	 * @param outputPath absolute path of the zip file
	 * 
	 *@see {@link #zipDir(String, String, File, ZipOutputStream)}
	 * 
	 */
	public void zip(String inputPath, String outputPath){
		try {
			this.dirToZip = inputPath;
			this.zipFile = outputPath;
			File file = new File(zipFile + ".zip");
			ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
			zipDir(dirToZip, zipFile, new File(dirToZip), zipOutputStream);
			zipOutputStream.close();
		}catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param dirToZip absolute path of the directory to zip
	 * @param zipFile absolute path of the zip file
	 * @param dirToZipFile
	 * @param zipOutputStream
	 */
	private void zipDir(String dirToZip, String zipFile, File dirToZipFile, ZipOutputStream zipOutputStream) {
		if (zipFile == null || dirToZip == null || dirToZipFile == null || zipOutputStream == null || !dirToZipFile.isDirectory()) return;

		BufferedInputStream fileInputStream = null;
		try {
			File[] fileArray = dirToZipFile.listFiles();
			String path;
			for (File file : fileArray) {
				if (file.isDirectory()) {
					zipDir(dirToZip, zipFile, file, zipOutputStream);
					continue;
				}
				fileInputStream = new BufferedInputStream(new FileInputStream(file));
				path = file.getCanonicalPath();
				String name = path.substring(dirToZip.length(), path.length());
				System.out.println("zip " + name);
				zipOutputStream.putNextEntry(new ZipEntry(name));
				int len;
				byte[] buffer = new byte[fileInputStream.available()];
				while ((len = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
					zipOutputStream.write(buffer, 0, len);
					zipOutputStream.closeEntry();
				}
				fileInputStream.close();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	 

	/**
	 * Extracts all files of a zip file.
	 * 
	 * @param zip absolute path of the zip file
	 * @param output absolute path of the directory, where the zip file will be extracted
	 * @param dirName name of directory containing all extracted files
	 */
	public static void extractFiles(String zipFile, String output, String dirName){
		
		String separator = System.getProperty("file.separator");
		
		if (!(output.endsWith("/") || output.endsWith("\\"))) {
			output += separator;
		}
		
		String path = output+dirName+separator;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try{
			File dir = new File(path);
			if(!dir.exists()){
				FileOperations.createFolder(path);
			}
			
			ZipFile file = new ZipFile(zipFile);
			Enumeration enu = file.entries();
			while(enu.hasMoreElements()){
				ZipEntry zipEntry = (ZipEntry)enu.nextElement();
				in = new BufferedInputStream(file.getInputStream(zipEntry));
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
		}catch(IOException | FileNotCreatedException | FileAlreadyExistsException e){
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
