package com.qiluomite.mywechat.message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
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
		StringBuilder line = new StringBuilder();
		line.append(DateKit.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss")+"\r\n");
		line.append(fromNickName);
		line.append("-" + memberNickName + ":" + content+"\r\n");

		try {
			
			Files.write(Paths.get(meta.getConfig().get("app.msg_location")), line.toString().getBytes(),
					StandardOpenOption.CREATE,StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (com.qiluomite.mywechat.config.Config.AUTO_REPLY) {
			String fromUser = msg.getString("FromUserName");
			String ans = reply(fromUser, content);
			LOGGER.info("回复:" + memberNickName + "：" + ans);
			webwxsendmsg(ans, msg.getString("FromUserName"));
		}

	}

}
