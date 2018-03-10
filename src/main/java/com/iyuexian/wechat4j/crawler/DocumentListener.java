package com.iyuexian.wechat4j.crawler;

import org.jsoup.nodes.Document;

public interface DocumentListener {
	public void onDownload(String url,Document doc);
}
