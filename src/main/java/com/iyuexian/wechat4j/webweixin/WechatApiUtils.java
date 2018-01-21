package com.iyuexian.wechat4j.webweixin;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONKit;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatException;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.http.WechatHttpClient;
import com.iyuexian.wechat4j.util.CookieUtil;
import com.iyuexian.wechat4j.util.Matchers;

public class WechatApiUtils {
	static final Logger LOGGER = LoggerFactory.getLogger(WechatApiUtils.class);

	public static String getUUID() throws WechatException {
		HttpRequest request = HttpRequest.get(Constant.JS_LOGIN_URL, true, "appid", "wx782c26e4c19acffb", "fun", "new", "lang", "zh_CN", "_",
				DateKit.getCurrentUnixTime());
		String res = request.body();
		request.disconnect();
		if (StringKit.isBlank(res)) {
			throw new WechatException("获取UUID失败");
		}
		String code = Matchers.match("window.QRLogin.code = (\\d+);", res);
		if (null == code || !code.equals("200")) {
			throw new WechatException("获取UUID失败，" + code);
		}
		String uuid = Matchers.match("window.QRLogin.uuid = \"(.*)\";", res);
		return uuid;
	}

	public static String getQrCode(String uuid) throws WechatException {

		if (StringKit.isBlank(uuid)) {
			throw new WechatException("请先获取UUID");
		}
		String url = Constant.QRCODE_URL + uuid;
		final File output = new File("temp.jpg");
		HttpRequest.post(url, true, "t", "webwx", "_", DateKit.getCurrentUnixTime()).receive(output);

		if (null == output || !output.exists() || !output.isFile()) {
			throw new WechatException("获取登陆二维码失败");
		}
		return output.getPath();

	}

	public static String waitLogin(int tip, String uuid) {
		String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";
		HttpRequest request = HttpRequest.get(url, true, "tip", tip, "uuid", uuid, "_", DateKit.getCurrentUnixTime());
		LOGGER.warn("等待登陆");
		String res = request.body();
		request.disconnect();
		return res;

	}

	public static WechatMeta newWechatMeta(String waitloginRes) {
		String pm = Matchers.match("window.redirect_uri=\"(\\S+?)\";", waitloginRes);
		String redirectUrl = pm + "&fun=new";
		String baseUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("/"));
		WechatMeta meta = new WechatMeta();
		meta.setRedirect_uri(redirectUrl);
		meta.setBase_uri(baseUrl);
		meta.setLoginDate(new Date());
		return meta;
	}

	public static void login(WechatMeta meta) throws WechatException {

		if (StringKit.isBlank(meta.getRedirect_uri())) {
			throw new WechatException("redirect_url不能为空");
		}

		HttpRequest request = HttpRequest.get(meta.getRedirect_uri());
		String res = request.body();
		meta.setCookie(CookieUtil.getCookie(request));
		request.disconnect();
		if (StringKit.isBlank(res)) {
			throw new WechatException("登录失败");
		}
		meta.setSkey(Matchers.match("<skey>(\\S+)</skey>", res));
		meta.setWxsid(Matchers.match("<wxsid>(\\S+)</wxsid>", res));
		meta.setWxuin(Matchers.match("<wxuin>(\\S+)</wxuin>", res));
		meta.setPass_ticket(Matchers.match("<pass_ticket>(\\S+)</pass_ticket>", res));

		JSONObject baseRequest = new JSONObject();
		baseRequest.put("Uin", meta.getWxuin());
		baseRequest.put("Sid", meta.getWxsid());
		baseRequest.put("Skey", meta.getSkey());
		baseRequest.put("DeviceID", meta.getDeviceId());
		meta.setBaseRequest(baseRequest);
	}

	public static JSONObject wxInit(WechatMeta meta) throws WechatException {
		String url = meta.getBase_uri() + "/webwxinit?r=" + DateKit.getCurrentUnixTime() + "&lang=zh_CN";
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie())
				.send(body.toString());
		String res = request.body();
		request.disconnect();

		JSONObject jsonObject = null;
		if (StringKit.isBlank(res) || (jsonObject = JSONKit.parseObject(res)) == null) {
			throw new WechatException("微信初始化失败");
		}

		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null == BaseResponse || BaseResponse.getInt("Ret", -1) != 0) {
			throw new WechatException("微信初始化失败," + BaseResponse);
		}

		meta.setSyncKey(jsonObject.get("SyncKey").asJSONObject());
		meta.setUser(jsonObject.get("User").asJSONObject());

		StringBuffer synckey = new StringBuffer();
		JSONArray list = meta.getSyncKey().get("List").asArray();
		for (int i = 0, len = list.size(); i < len; i++) {
			JSONObject item = list.get(i).asJSONObject();
			synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
		}
		meta.setSynckey(synckey.substring(1));
		return jsonObject;
	}

	public static void openStatusNotify(WechatMeta meta) throws WechatException {
		String url = meta.getBase_uri() + "/webwxstatusnotify?lang=zh_CN&pass_ticket=" + meta.getPass_ticket();

		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("Code", 3);
		body.put("FromUserName", meta.getUser().getString("UserName"));
		body.put("ToUserName", meta.getUser().getString("UserName"));
		body.put("ClientMsgId", DateKit.getCurrentUnixTime());

		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie())
				.send(body.toString());
		String res = request.body();
		request.disconnect();

		if (StringKit.isBlank(res)) {
			throw new WechatException("状态通知开启失败");
		}

		JSONObject jsonObject = JSONKit.parseObject(res);
		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null == BaseResponse || BaseResponse.getInt("Ret", -1) != 0) {
			throw new WechatException("状态通知开启失败");
		}
		LOGGER.debug("状态通知已开启");
	}

	public static int[] syncCheck(WechatMeta meta, String url) {
		int[] arr = new int[] { -1, -1 };
		try {
			url = url == null ? meta.getWebpush_url() + "/synccheck" : "https://" + url + "/cgi-bin/mmwebwx-bin/synccheck";
			JSONObject body = new JSONObject();
			body.put("BaseRequest", meta.getBaseRequest());
			HttpRequest request = HttpRequest
					.get(url, true, "r", DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5), "skey", meta.getSkey(), "uin", meta.getWxuin(),
							"sid", meta.getWxsid(), "deviceid", meta.getDeviceId(), "synckey", meta.getSynckey(), "_", System.currentTimeMillis())
					.header("Cookie", meta.getCookie());
			String res = request.body();
			request.disconnect();
			if (StringKit.isBlank(res)) {
				return arr;
			}

			String retcode = Matchers.match("retcode:\"(\\d+)\",", res);
			String selector = Matchers.match("selector:\"(\\d+)\"}", res);
			if (null != retcode && null != selector) {
				arr[0] = Integer.parseInt(retcode);
				arr[1] = Integer.parseInt(selector);
				return arr;
			}
			return arr;
		} catch (Exception ex) {
			return arr;
		}
	}

	public static int[] syncCheck(WechatMeta meta) throws WechatException {
		return syncCheck(meta, null);
	}

	public static void choiceSyncLine(WechatMeta meta) throws WechatException {
		boolean enabled = false;
		for (String syncUrl : Constant.SYNC_HOST) {
			int[] res = syncCheck(meta, syncUrl);
			if (res[0] == 0) {
				String url = "https://" + syncUrl + "/cgi-bin/mmwebwx-bin";
				meta.setWebpush_url(url);
				LOGGER.info("选择线路：[{}]", syncUrl);
				enabled = true;
				break;
			}
		}
		if (!enabled) {
			throw new WechatException("同步线路不通畅");
		}
	}

	public static JSONObject webwxsync(WechatMeta meta) throws WechatException {
		String url = meta.getBase_uri() + "/webwxsync?skey=" + meta.getSkey() + "&sid=" + meta.getWxsid();

		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("SyncKey", meta.getSyncKey());
		body.put("rr", DateKit.getCurrentUnixTime());

		WechatHttpClient httpRequsetUtil = new WechatHttpClient(meta);
		JSONObject jsonObject = httpRequsetUtil.postJSON(url, body);
		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null != BaseResponse) {
			int ret = BaseResponse.getInt("Ret", -1);
			if (ret == 0) {
				meta.setSyncKey(jsonObject.get("SyncKey").asJSONObject());
				StringBuffer synckey = new StringBuffer();
				JSONArray list = meta.getSyncKey().get("List").asArray();
				for (int i = 0, len = list.size(); i < len; i++) {
					JSONObject item = list.get(i).asJSONObject();
					synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
				}
				meta.setSynckey(synckey.substring(1));
				return jsonObject;
			}
		}
		return null;
	}

}
