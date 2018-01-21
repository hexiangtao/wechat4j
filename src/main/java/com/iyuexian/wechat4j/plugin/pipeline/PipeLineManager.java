package com.iyuexian.wechat4j.plugin.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.config.Enums.MsgType;

public class PipeLineManager {
	private List<PipeLine> pipelines;

	static class Holder {
		static PipeLineManager instance = new PipeLineManager();
	}

	public static PipeLineManager instance() {
		return Holder.instance;
	}

	private PipeLineManager() {
		pipelines = new ArrayList<PipeLine>();
		pipelines.add(new DBPipeLine());
	}

	public void process(WechatMeta meta, String line, MsgType msgType) {
		for (PipeLine pipeLine : pipelines) {
			if (pipeLine != null) {
				pipeLine.processs(meta, line, msgType);
			}

		}
	}
}
