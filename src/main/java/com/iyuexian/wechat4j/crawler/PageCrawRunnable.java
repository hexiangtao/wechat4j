package com.iyuexian.wechat4j.crawler;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageCrawRunnable implements Runnable {

	private LinkCollection linkCollection;
	private int maxSize = 100000;
	private final int waitMaxCount;
	private int currentWiatCount = 0;
	private List<DocumentListener> listeners;

	public PageCrawRunnable(LinkCollection linkCollection) {
		this(linkCollection, null);
	}

	public PageCrawRunnable(LinkCollection linkCollection, List<DocumentListener> listeners) {
		this(linkCollection, 10, listeners);
	}

	public PageCrawRunnable(LinkCollection linkCollection, int waitMaxCount, List<DocumentListener> listeners) {
		this.linkCollection = linkCollection;
		this.waitMaxCount = waitMaxCount;
		this.listeners = listeners;
	}

	public boolean isFetchedFull() {
		return linkCollection.getFetched().size() >= maxSize;
	}

	@Override
	public void run() {
		while (!isFetchedFull() && (currentWiatCount < waitMaxCount)) {
			String link = linkCollection.poll();
			if (link == null) {
				this.currentWiatCount++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			this.currentWiatCount = 0;
			linkCollection.setFetched(link);
			Logger.debug("get:{}", link);
			try {
				Document doc = Jsoup.connect(link).get();
				pubEvent(link,doc);
			} catch (Exception e) {
				Logger.error("抓取页面失败:{}", e.getLocalizedMessage());
			}
		}

	}

	public void pubEvent(String url,Document doc) {
		if (listeners == null || listeners.size() == 0) {
			return;
		}
		for (DocumentListener documentListener : listeners) {
			documentListener.onDownload(url,doc);
		}

	}

}
