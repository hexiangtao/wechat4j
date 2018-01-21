package com.iyuexian.wechat4j.plugin.pipeline;

import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.webweixin.dao.MessageDao;
import com.iyuexian.wechat4j.webweixin.entity.Message;

public class DBPipeLine implements PipeLine {
	private MessageDao messageDao = new MessageDao();

	@Override
	public void processs(WechatMeta meta, String line, MsgType msgType) {
		if (meta.getMobile() == null || meta.getMobile().trim().length() == 0) {
			return;
		}
		Message msg = new Message(meta.getMobile(), line, msgType.getType(), msgType.getName());
		messageDao.insert(msg);
		messageDao.commint();
	}

}
