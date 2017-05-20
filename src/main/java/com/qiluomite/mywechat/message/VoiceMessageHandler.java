package com.qiluomite.mywechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;

public class VoiceMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoiceMessageHandler.class);

	public VoiceMessageHandler(WechatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.warn("开始处理语音消息");
		webwxsendmsg("暂时无法处理语音消息", msg.getString("FromUserName"));
	}

}
