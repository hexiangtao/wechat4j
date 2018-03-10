package com.iyuexian.wechat4j.crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PageCrawer {

	private LinkCollection linkCollection;
	private int threadNum = 3;
	private List<PageCrawRunnable> tasks;
	private ExecutorService threadPool;

	private List<DocumentListener> listeners;

	public PageCrawer(String host, DocumentListener... listeners) {
		this(host, 1, listeners);
	}

	public PageCrawer(String host, int threadNum, DocumentListener... listener) {
		this.threadNum = threadNum;
		this.linkCollection = new LinkCollection();
		this.tasks = new ArrayList<PageCrawRunnable>(threadNum);

		if (listener != null && listener.length > 0) {
			this.listeners = Arrays.asList(listener);
		} else {
			this.listeners = Arrays.asList(new DefaultDocumentListener(linkCollection, host));
		}

		String firtUrl = host.startsWith("http") ? host : host.startsWith("https:") ? host : "http://" + host;
		linkCollection.offer(firtUrl);
		this.threadPool = Executors.newScheduledThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			tasks.add(new PageCrawRunnable(linkCollection, this.listeners));
		}
	}

	public void start() {
		try {
			long timestamp = System.currentTimeMillis();

			List<Future<?>> futureList = new ArrayList<Future<?>>();
			for (PageCrawRunnable task : tasks) {
				Future<?> future = threadPool.submit(task);
				futureList.add(future);
			}
			for (Future<?> future : futureList) {
				future.get();
			}

			Logger.info("耗时:{}秒,抓取完毕，共{}条网页", (System.currentTimeMillis() - timestamp) / 1000, linkCollection.getFetched().size());
			for (String item : linkCollection.getFetched()) {
				Logger.info("{}", item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.shutdown();
		}
	}

	public void shutdown() {
		threadPool.shutdown();
	}

	public LinkCollection getLinkCollection() {
		return linkCollection;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public List<PageCrawRunnable> getTasks() {
		return tasks;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

}
