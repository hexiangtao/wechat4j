package com.iyuexian.wechat4j.plugin.pipeline;

import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.config.Enums.MsgType;

public interface PipeLine {
	public void processs(WechatMeta meata, String line, MsgType msgType);

}
