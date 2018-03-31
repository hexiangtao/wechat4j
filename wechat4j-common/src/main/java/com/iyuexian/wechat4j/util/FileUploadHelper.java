package com.iyuexian.wechat4j.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.iyuexian.wechat4j.config.Constant;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class FileUploadHelper {

	private String ak = Constant.configReader.get(Constant.QINIU_AK);
	private String secret = Constant.configReader.get(Constant.QINIU_SK);
	private String bucket = Constant.configReader.get(Constant.QINIU_BUCKET_IMAGE0);
	private UploadManager uploadManager = new UploadManager();

	public FileUploadHelper(String ak, String secret, String bucket, UploadManager uploadManager) {
		if (ak != null && ak.trim().length() > 0)
			this.ak = ak;

		if (secret != null && secret.trim().length() > 0)
			this.secret = secret;

		if (bucket != null && bucket.trim().length() > 0)
			this.bucket = bucket;

		if (uploadManager != null) {
			this.uploadManager = uploadManager;
		}

	}

	public FileUploadHelper(String ak, String secret, String bucket) {
		this(ak, secret, bucket, null);
	}

	public FileUploadHelper(String ak, String secret) {
		this(ak, secret, null, null);
	}

	public FileUploadHelper() {
		this(null, null, null, null);
	}

	public static String generateFileKey(String fileName) {
		String ext = fileName.lastIndexOf(".") > 0 ? fileName.substring(fileName.lastIndexOf(".")) : "";
		return Calendar.getInstance().get(Calendar.YEAR) + "/" + UUID.randomUUID().toString().replace("-", "").toLowerCase() + ext;
	}

	public String uploadToQiniu(InputStream is, String fileName) {
		try {
			String host = Constant.configReader.get(Constant.QIUNIU_IMG_URL);
			String fileKey = generateFileKey(fileName);
			Auth auth = Auth.create(ak, secret);
			String uploadToken = auth.uploadToken(bucket);
			byte[] buffer = new byte[is.available()];
			IOUtils.readFully(is, buffer);
			Response response = uploadManager.put(buffer, fileKey, uploadToken);
			return response.isOK() ? host + fileKey : "";
		} catch (Exception ex) {
			return "";
		}

	}

	public String uploadToQiniu(byte[] bytes, String originalName) {
		try {
			Auth auth = Auth.create(ak, secret);
			String uploadToken = auth.uploadToken(bucket);
			String fileKey = generateFileKey(originalName);
			Response response = uploadManager.put(bytes, fileKey, uploadToken);
			if (!response.isOK()) {
				return "";
			}
			String host = Constant.configReader.get(Constant.QIUNIU_IMG_URL);
			return host + fileKey;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

}
