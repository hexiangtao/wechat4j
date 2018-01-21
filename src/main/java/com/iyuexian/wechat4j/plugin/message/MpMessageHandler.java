package com.iyuexian.wechat4j.plugin.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatMeta;

public class MpMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoiceMessageHandler.class);

	public MpMessageHandler(WechatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.info("do nothing");
		webwxsendmsg("不知道你在说什么", msg.getString("FromUserName"));
	}

	

}
