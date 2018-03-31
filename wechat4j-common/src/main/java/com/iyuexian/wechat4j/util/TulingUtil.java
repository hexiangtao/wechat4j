package com.iyuexian.wechat4j.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.blade.kit.Base64;

public class TulingUtil {

	/**
	 * 向后台发送post请求
	 * 
	 * @param param
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPost(String param, String url) throws Exception {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(50000);
		conn.setReadTimeout(50000);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "token");
		conn.setRequestProperty("tag", "htc_new");
		conn.connect();
		out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		out.write(param);
		out.flush();
		out.close();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}

	/**
	 * 加密方法
	 * 
	 * 说明：采用128位
	 * 
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String encrypt(String strKey, String strContent) throws Exception {

		Key key = new SecretKeySpec(getHash("MD5", strKey), "AES");
		IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] data = strContent.getBytes("UTF-8");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] encryptData = cipher.doFinal(data);
		String encryptResult = new String(Base64.encodeBytes(encryptData));
		return encryptResult;
	}

	/**
	 * 
	 * @param algorithm
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static byte[] getHash(String algorithm, String text) throws Exception {
		byte[] bytes = text.getBytes("UTF-8");
		final MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.update(bytes);
		return digest.digest();
	}

	/**
	 * MD5加密算法
	 * 
	 * 说明：32位加密算法
	 * 
	 * @param 待加密的数据
	 * @return 加密结果，全小写的字符串
	 * @throws Exception
	 */
	public static String MD5(String s) throws Exception {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] btInput = s.getBytes("utf-8");
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
}
