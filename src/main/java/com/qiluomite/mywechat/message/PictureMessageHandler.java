package com.qiluomite.mywechat.message;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.FileKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.util.PropertyReader;

public class PictureMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureMessageHandler.class);

	public PictureMessageHandler(WechatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.warn("开始处理图片消息");
		PropertyReader pr=PropertyReader.load("classpath:config.properties");
		String imgDir = pr.get("app.img_path");
		String msgId = msg.getString("MsgId");
		FileKit.createDir(imgDir, false);
		String imgUrl = meta.getBase_uri() + "/webwxgetmsgimg?MsgID=" + msgId + "&skey=" + meta.getSkey() + "&type=slave";
		HttpRequest.get(imgUrl).header("Cookie", meta.getCookie()).receive(new File(imgDir + "/" + msgId + ".jpg"));
		
		
	}
}
