package com.qiluomite.mywechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;

public class TxtMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(TxtMessageHandler.class);

	public TxtMessageHandler(WechatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		String content = msg.getString("Content");

		if (!preHandle(msg)) {
			return;
		}

		String fromNickName = getUserRemarkName(msg.getString("FromUserName"));
		String memberNickName = getMemberNickName(msg);

		String[] contentArray = content.split(":");

		if (contentArray.length == 0 || contentArray.length == 1) {
			LOGGER.warn(fromNickName + ": " + content);
		} else {
			content = contentArray[1].replace("<br/>", "\n");
			LOGGER.warn('\n' + fromNickName + "-" + memberNickName + ": " + content);

		}
		if (com.qiluomite.mywechat.config.Config.AUTO_REPLY) {
			String fromUser = msg.getString("FromUserName");
			if (fromUser.contains("深度")) {
				return;
			}
			String ans = reply(fromUser, content);
			LOGGER.info("回复:" + memberNickName + "：" + ans);
			webwxsendmsg(ans, msg.getString("FromUserName"));
		}

	}

}
