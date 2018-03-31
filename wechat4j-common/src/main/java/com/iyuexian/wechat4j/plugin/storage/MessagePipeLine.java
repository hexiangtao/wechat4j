package com.iyuexian.wechat4j.plugin.storage;

import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.core.WechatMeta;

public interface MessagePipeLine {
	public void processs(WechatMeta meata, String line, MsgType msgType);

}
