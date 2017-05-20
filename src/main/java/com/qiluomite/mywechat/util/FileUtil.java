package com.qiluomite.mywechat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	protected static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };



	public static final String getContentType(Path path, String defaultValue) {
		try {
			return Files.probeContentType(path);
		} catch (IOException e) {
			return defaultValue;
		}
	}

	public static boolean isVideo(String contentType) {
		// TODO
		return false;
	}

	public static boolean isPicture(String contentType) {
		if (contentType == null)
			return false;
		return contentType.contains("image");
	}

	public static boolean isDoc(String contentType) {
		if (contentType == null)
			return false;
		return contentType.contains("document") || contentType.contains("vnd.openxml");
	}

	/**
	 * 计算文件的MD5码
	 * 
	 * @param file
	 * @return
	 */
	public static String getMD5(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			return new String(encodeHex(md.digest()));
		} catch (IOException ex) {
			logger.warn(ex.toString());
			return null;
		} catch (NoSuchAlgorithmException ex) {
			logger.warn(ex.toString());
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				logger.warn(ex.toString());
			}
		}
	}

	private static char[] encodeHex(byte[] bytes) {
		char chars[] = new char[32];
		for (int i = 0; i < chars.length; i = i + 2) {
			byte b = bytes[i / 2];
			chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
			chars[i + 1] = HEX_CHARS[b & 0xf];
		}
		return chars;
	}

	/**
	 * 得到文件的SHA码,用于校验
	 * 
	 * @param file
	 * @return
	 */
	public static String getSHA(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			return new String(encodeHex(md.digest()));
		} catch (IOException ex) {
			logger.warn(ex.toString());
			return null;
		} catch (NoSuchAlgorithmException ex) {
			logger.warn(ex.toString());
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				logger.warn(ex.toString());
			}
		}
	}

}
