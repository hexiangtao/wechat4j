package com.iyuexian.wechat4j.plugin.storage;

import java.util.ArrayList;
import java.util.List;

import com.iyuexian.wechat4j.config.Enums.MsgType;
import com.iyuexian.wechat4j.core.WechatMeta;

public class PipeLineManager {
	private List<MessagePipeLine> pipelines;

	static class Holder {
		static PipeLineManager instance = new PipeLineManager();
	}

	public static PipeLineManager instance() {
		return Holder.instance;
	}

	private PipeLineManager() {
		pipelines = new ArrayList<MessagePipeLine>();
	}

	public void process(WechatMeta meta, String line, MsgType msgType) {
		for (MessagePipeLine pipeLine : pipelines) {
			if (pipeLine != null) {
				pipeLine.processs(meta, line, msgType);
			}

		}
	}
}
