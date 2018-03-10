package com.iyuexian.wechat4j.crawler;

public class Test1 {

	public static void main(String[] args) {
		String host = "v.qq.com";
		PageCrawer pageCrawer = new PageCrawer(host, 15, new DefaultDocumentListener(host, "D:/test.txt"));
		pageCrawer.start();
	}
}
