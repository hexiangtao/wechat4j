package com.qiluomite.mywechat.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.exception.WechatException;
import com.qiluomite.mywechat.message.IMessageHandler;
import com.qiluomite.mywechat.message.MessageHandlerFactory;

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
				throw new WechatException("无法找到对应的消息处理器");
			}
			messageHandler.process(msg);
		}
	}

}
