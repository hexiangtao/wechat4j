package com.iyuexian.wechat4j.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.exception.WechatException;
import com.iyuexian.wechat4j.message.IMessageHandler;
import com.iyuexian.wechat4j.message.MessageHandlerFactory;
import com.iyuexian.wechat4j.model.WechatMeta;

public class MessageHandler {

	protected Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	private MessageHandlerFactory messageFactory;

	public MessageHandler(WechatMeta wechatMeta) {
		super();
		messageFactory = new MessageHandlerFactory(wechatMeta);

	}

	public void handleMsg(JSONObject data) {
		if (null == data) {
			logger.warn("data不能为空");
		}
		JSONArray AddMsgList = data.get("AddMsgList").asArray();
		for (int i = 0, len = AddMsgList.size(); i < len; i++) {
			JSONObject msg = AddMsgList.get(i).asJSONObject();
			int msgType = msg.getInt("MsgType", 0);

			if (msgType == 51) {
				return;
			}
			IMessageHandler messageHandler = messageFactory.getMessageHandler(msgType);
			if (messageHandler == null) {
				logger.error("msgType:{},无法找到对应的消息处理器", msgType);
				return;
			}
			messageHandler.process(msg);
		}
	}

}
