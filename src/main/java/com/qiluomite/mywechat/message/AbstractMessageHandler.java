package com.qiluomite.mywechat.message;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.client.RecordContainer;
import com.qiluomite.mywechat.component.Storage;
import com.qiluomite.mywechat.config.Config;
import com.qiluomite.mywechat.config.Constant;

public abstract class AbstractMessageHandler implements IMessageHandler, TxtMessageRobot {

	public static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);
	protected WechatMeta meta;

	public AbstractMessageHandler(WechatMeta meta) {
		super();
		this.meta = meta;
	}

	public void webwxsendmsg(String content, String to) {

		if (!Config.AUTO_REPLY) {
			LOGGER.warn("auto reply setting was off,please change the Config setting--");
		}
		String url = meta.getBase_uri() + "/webwxsendmsg?lang=zh_CN&pass_ticket=" + meta.getPass_ticket();
		JSONObject body = new JSONObject();
		RecordContainer.cache.add(to);
		String clientMsgId = DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5);
		JSONObject Msg = new JSONObject();
		Msg.put("Type", 1);
		Msg.put("Content", content);
		Msg.put("FromUserName", meta.getUser().getString("UserName"));
		Msg.put("ToUserName", to);
		Msg.put("LocalID", clientMsgId);
		Msg.put("ClientMsgId", clientMsgId);

		body.put("BaseRequest", meta.getBaseRequest());
		body.put("Msg", Msg);
		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie",
				meta.getCookie());
		new Thread(() -> {
			try {
				Thread.sleep(3000);
				request.send(body.toString());
				String msgResult = request.body();
				LOGGER.warn("msgResult:{}", msgResult);
				request.disconnect();
			} catch (Exception ex) {
			}
		}).start();
	}

	public boolean download(JSONObject msg, MsgType msgType) {

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", meta.getCookie());
		Map<String, Object> params = new HashMap<String, Object>();

		String ext = "";
		if (msgType == MsgType.PICTURE) {
			ext = "jpg";
		} else if (msgType == MsgType.VOICE) {
			ext = "mp3";
		} else if (msgType == MsgType.VIDEO || msgType == MsgType.SMALL_VIDEO) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			headers.put("Range", "bytes=0-");
			ext = "mp4";
		}
		if (msgType == MsgType.MEDIA) {
			headers.put("Range", "bytes=0-");
			params.put("sender", msg.getString("FromUserName"));
			params.put("mediaid", msg.getString("MediaId"));
			params.put("filename", msg.getString("FileName"));
		}
		params.put("msgid", msg.getString("MsgId"));
		params.put("skey", meta.getSkey());

		String dirPath = meta.getConfig().get("app.media_path");
		String filePath = dirPath + "/" + DateKit.dateFormat(new Date(), "yyyyMMddHHmmss_SSS") + "." + ext;
		String host = meta.getBase_uri().endsWith("/") ? meta.getBase_uri() : meta.getBase_uri() + "/";
		String url = host + msgType.getDownloadPath() + "?MsgID=" + msg.getString("MsgId") + "&skey=" + meta.getSkey()
				+ "&type=slave";
		 LOGGER.info("download url:{}",url);
		HttpRequest.get(url, params, true).headers(headers).receive(new File(filePath));
		return true;
	}

	public String getUserRemarkName(String id) {
		String name = "unknow";
		for (int i = 0, len = Storage.instance().getAllContact().size(); i < len; i++) {
			JSONObject member = Storage.instance().getAllContact().get(i).asJSONObject();
			if (!member.getString("UserName").equals(id)) {
				continue;
			}
			if (StringKit.isNotBlank(member.getString("RemarkName"))) {
				name = member.getString("RemarkName");
			} else {
				name = member.getString("NickName");
			}
			return name;
		}
		return name;
	}

	public String getMemberRemarkName(String chatRoomUserName, String memberUserName) {
		JSONArray chatRoomList = Storage.instance().getLatestChatRoomList();
		for (int i = 0; i < chatRoomList.size(); i++) {
			JSONObject chatroom = chatRoomList.get(i).asJSONObject();
			if (!chatroom.getString("UserName").equals(chatRoomUserName)) {
				continue;
			}
			JSONArray memberList = chatroom.get("MemberList").asArray();
			for (int j = 0; j < memberList.size(); j++) {
				JSONObject member = memberList.get(j).asJSONObject();
				if (member.getString("UserName").equals(memberUserName)) {
					return member.getString("NickName");
				}
			}
		}
		return "未知昵称";

	}

	public boolean preHandle(JSONObject msg) {
		if (Constant.FILTER_USERS.contains(msg.getString("ToUserName"))) {
			LOGGER.info("你收到一条被过滤的消息");
			return false;
		}

		if (isSlefSend(msg)) {
			LOGGER.info("你发送了一条消息 ");
			return true;
		}

		if (msg.getString("FromUserName").indexOf("@@") != -1) {
			LOGGER.info("您收到一条群聊消息");
			return true;
		}

		LOGGER.warn("您收到一条 未知类型消息");
		return true;

	}

	public boolean isSlefSend(JSONObject msg) {
		return msg.getString("FromUserName").equals(this.meta.getUser().getString("UserName"));

	}

	public String getMemberNickName(JSONObject msg) {

		if (this.meta.getUser().getString("UserName").equals(msg.getString("FromUserName"))) {
			return this.meta.getUser().getString("NickName");
		}

		if (isGroupMsg(msg)) {
			String memberUserName = msg.getString("Content").split(":")[0];
			return getMemberRemarkName(msg.getString("FromUserName"), memberUserName);
		} else {
			return getUserRemarkName(msg.getString("FromUserName"));
		}
	}

	public boolean isGroupMsg(JSONObject msg) {
		return msg.getString("FromUserName").startsWith("@@");
	}

	@Override
	public String reply(String uid, String content) {
		return TulingRobot.instance().chat(uid, content);
	}

}
