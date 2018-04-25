package org.sidiff.remote.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumUtil {

	public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException {

		// Use MD5 algorithm
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");

		FileInputStream fis = new FileInputStream(file);

		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		while ((bytesCount = fis.read(byteArray)) != -1) {
			md5Digest.update(byteArray, 0, bytesCount);
		}

		fis.close();

		byte[] bytes = md5Digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
