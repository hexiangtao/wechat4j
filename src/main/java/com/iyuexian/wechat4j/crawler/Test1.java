package com.iyuexian.wechat4j.crawler;

public class Test1 {

	public static void main(String[] args) {
		PageCrawer pageCrawer = new PageCrawer("bj.58.com", 15);
		pageCrawer.start();
	}
}
