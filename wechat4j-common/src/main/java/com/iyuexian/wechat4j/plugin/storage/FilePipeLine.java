package com.iyuexian.wechat4j.plugin.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.core.WechatMeta;

public class FilePipeLine implements MessagePipeLine {

	@Override
	public void processs(WechatMeta meta, String line, MsgType msgType) {

		File f = new File(Constant.configReader.get("app.msg_location"));
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			Files.write(Paths.get(Constant.configReader.get("app.msg_location")), line.toString().getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
