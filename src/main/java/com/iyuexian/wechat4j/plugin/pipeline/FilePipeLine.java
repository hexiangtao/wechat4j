package com.iyuexian.wechat4j.plugin.pipeline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.config.Enums.MsgType;

public class FilePipeLine implements PipeLine {

	@Override
	public void processs(WechatMeta meta,String line,MsgType msgType) {
		try {
			Files.write(Paths.get(Constant.configReader.get("app.msg_location")), line.toString().getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.WRITE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
