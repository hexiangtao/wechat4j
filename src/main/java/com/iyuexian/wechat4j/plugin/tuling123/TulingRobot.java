package com.iyuexian.wechat4j.plugin.tuling123;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSON;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.util.TulingUtil;

public class TulingRobot {
	public static final Logger LOGGER = LoggerFactory.getLogger(TulingRobot.class);
	private String key = "7507257e2872417d9e4fb0e2764cf0ac";
	private String secret = "36c3be794b4950dc";
	private String url = "http://www.tuling123.com/openapi/api";
	private static TulingRobot instance = null;

	private static final int V1 = 1;
	private static final int V2 = 2;

	private TulingRobot() {
	}

	public static TulingRobot instance() {
		if (instance != null) {
			return instance;
		}
		instance = new TulingRobot();
		return instance;
	}

	public String chat(String userId, String content) {
		return chat(userId, content, null);
	}

	public String chat(String userId, String content, Location location) {
		if (content == null)
			throw new IllegalArgumentException("content不能为null");
		try {
			JSONObject params = populateV2Param(content, userId);
			String resp = send(params, V1);
			if (resp == null)
				return "";
			return JSON.parse(resp).asJSONObject().getString("text");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

	}

	public JSONObject populateV2Param(String content, String userId) {

		JSONObject params = new JSONObject();
		params.put("key", key);
		params.put("info", content);
		params.put("userid", userId);
		return params;
	}

	public JSONObject populateV2Param(String content, String userId, Location location) {
		JSONObject params = new JSONObject();
		JSONObject perception = new JSONObject();
		perception.put("inputText", new InputText(content));

		JSONObject userInfo = new JSONObject();
		userInfo.put("apiKey", key);
		new UserInfo(key, userId);
		params.put("userInfo", userInfo);
		params.put("perception", perception);

		if (location != null) {
			JSONObject selfInfo = new JSONObject();
			selfInfo.put("location", location);
			params.put("selfInfo", selfInfo);

		}
		return params;

	}

	public String encryptSend(JSONObject reqParams) throws Exception {
		long timestamp = System.currentTimeMillis();
		String keyParam = secret + timestamp + key;
		String aesKey = TulingUtil.MD5(keyParam);
		String data = TulingUtil.encrypt(aesKey, reqParams.toString());

		JSONObject req = new JSONObject();
		req.put("key", key);
		req.put("timestamp", String.valueOf(timestamp));
		req.put("data", data);
		return send(req, V2);

	}

	public String send(JSONObject params, int version) {

		if (version == V2) {
			url = url + "/v2";
		}

		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8");
		request.send(params.toString());
		String resp = request.body();
		request.disconnect();
		return resp;
	}

	// private String encryptReq(Map<String, String> reqParam) throws Exception
	// {
	// long timestamp = System.currentTimeMillis();
	// String keyParam = secret + timestamp + key;
	// String aesKey = TulingUtil.MD5(keyParam);
	// String data = TulingUtil.encrypt(aesKey, JSON.toJSONString(reqParam));
	// Map<String, String> req = new HashMap<String, String>();
	// req.put("key", key);
	// req.put("timestamp", String.valueOf(timestamp));
	// req.put("data", data);
	// String str =
	// TulingUtil.sendPost(com.alibaba.fastjson.JSON.toJSONString(req), url);
	// if (str == null || str.trim().length() == 0)
	// return "";
	//
	// JSONObject repJSON = JSON.parseObject(str);
	// // int code = repJSON.getIntValue("code");
	// String text = repJSON.getString("text");
	// return text;
	//
	// }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
