package com.iyuexian.wechat4j.plugin.message;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.core.WechatMeta;

public class MessageManager implements IMessageHandler {
	protected Logger logger = LoggerFactory.getLogger(MessageManager.class);

	private WechatMeta meta;
	private Map<MsgType, IMessageHandler> handlers = new HashMap<MsgType, IMessageHandler>();

	public MessageManager(WechatMeta meta) {
		this.meta = meta;
		register();
	}

	private IMessageHandler getMessageHandler(int type) {
		MsgType msgType = MsgType.getType(type);
		if (msgType == null)
			return handlers.get(MsgType.TXT);
		return handlers.get(msgType);

	}

	private void register() {
		handlers.put(MsgType.TXT, new TxtMessageHandler(meta));
		handlers.put(MsgType.TXT, new TxtMessageHandler(meta));
		handlers.put(MsgType.VOICE, new VoiceMessageHandler(meta));
		handlers.put(MsgType.PICTURE, new PictureMessageHandler(meta));
		handlers.put(MsgType.SHARE_MP, new MpMessageHandler(meta));
		VideoMessageHandler videoMessageHandler = new VideoMessageHandler(meta);
		handlers.put(MsgType.SMALL_VIDEO, videoMessageHandler);
		handlers.put(MsgType.VIDEO, videoMessageHandler);
	}

	@Override
	public void process(JSONObject data) {
		if (null == data) {
			logger.warn("data不能为空");
		}
		JSONArray AddMsgList = data.get("AddMsgList").asArray();
		for (int i = 0, len = AddMsgList.size(); i < len; i++) {
			JSONObject msg = AddMsgList.get(i).asJSONObject();
			processSingleMsg(msg);
		}

	}

	private void processSingleMsg(JSONObject msg) {
		int msgType = msg.getInt("MsgType", 0);
		if (msgType == 51) {
			logger.info("msgType:{},do nothing", msgType);
			return;
		}
		IMessageHandler messageHandler = getMessageHandler(msgType);
		if (messageHandler == null) {
			logger.error("msgType:{},无法找到对应的消息处理器", msgType);
			return;
		}
		messageHandler.process(msg);

	}

}
