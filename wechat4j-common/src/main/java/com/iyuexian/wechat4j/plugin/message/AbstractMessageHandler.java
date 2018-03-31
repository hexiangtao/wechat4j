package com.iyuexian.wechat4j.plugin.message;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.io.ByteArrayOutputStream;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.core.WechatMeta;
import com.iyuexian.wechat4j.plugin.Storage;
import com.iyuexian.wechat4j.plugin.TulingRobot;
import com.iyuexian.wechat4j.plugin.TxtMessageRobot;
import com.iyuexian.wechat4j.plugin.storage.PipeLineManager;
import com.iyuexian.wechat4j.util.FileUploadHelper;

public abstract class AbstractMessageHandler implements IMessageHandler, TxtMessageRobot {

	public static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);
	protected WechatMeta meta;

	private FileUploadHelper fileUploadHelper = new FileUploadHelper();

	public AbstractMessageHandler(WechatMeta meta) {
		super();
		this.meta = meta;
	}

	public String webwxsendmsg(String content, String to) {
		String url = meta.getBase_uri() + "/webwxsendmsg?lang=zh_CN&pass_ticket=" + meta.getPass_ticket();
		final JSONObject body = new JSONObject();
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
		final HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie());
		request.send(body.toString());
		String msgResult = request.body();
		request.disconnect();
		return msgResult;

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
			LOGGER.warn("暂时不能处理video类消息,msg:{}", msg);
			ext = "mp4";
		} else if (msgType == MsgType.MEDIA) {
			headers.put("Range", "bytes=0-");
			params.put("sender", msg.getString("FromUserName"));
			params.put("mediaid", msg.getString("MediaId"));
			params.put("filename", msg.getString("FileName"));
		}
		params.put("msgid", msg.getString("MsgId"));
		params.put("skey", meta.getSkey());
		String host = meta.getBase_uri().endsWith("/") ? meta.getBase_uri() : meta.getBase_uri() + "/";
		String url = host + msgType.getDownloadPath() + "?MsgID=" + msg.getString("MsgId") + "&skey=" + meta.getSkey() + "&type=slave";
		LOGGER.info(" begin download resource for url:{}", url);
		ByteArrayOutputStream bios = new ByteArrayOutputStream();
		HttpRequest.get(url, params, true).headers(headers).receive(bios);
		String fileKey = fileUploadHelper.uploadToQiniu(bios.toInputStream(), "file." + ext);
		PipeLineManager.instance().process(meta, fileKey, msgType);
		return true;
	}

	public String getUserRemarkName(String id) {
		String name = "";
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

		if (msg.getString("FromUserName").startsWith("@@")) {
			LOGGER.info("您收到一条群聊消息");
			return true;
		}
		if (msg.getString("FromUserName").startsWith("@")) {
			LOGGER.info("您收到一条好友消息");
			return true;
		}
		LOGGER.warn("您收到一条 未知类型消息:{}", msg.toString());
		return true;

	}

	public boolean isSlefSend(JSONObject msg) {
		return msg.getString("FromUserName").equals(this.meta.getUser().getString("UserName"));
	}

	public String getSelfNickName() {
		return this.meta.getUser().getString("NickName");
	}

	public String getGroupMemberName(JSONObject msg) {
		String memberUserName = msg.getString("Content").split(":")[0];
		return getMemberRemarkName(msg.getString("FromUserName"), memberUserName);
	}

	public String getMemberNickName(JSONObject msg) {
		if (isSlefSend(msg)) {
			return getSelfNickName();
		} else if (isGroupMsg(msg)) {
			return getGroupMemberName(msg);
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
