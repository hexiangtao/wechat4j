package com.qiluomite.mywechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;

public class MpMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoiceMessageHandler.class);

	public MpMessageHandler(WechatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.warn("开始处理公众号消息");
		webwxsendmsg("不知道你在说什么", msg.getString("FromUserName"));
	}

	

}
