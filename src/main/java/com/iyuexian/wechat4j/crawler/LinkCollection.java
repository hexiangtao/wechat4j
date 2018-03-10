package com.iyuexian.wechat4j.crawler;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LinkCollection {
	private Set<String> fetched;
	private Queue<String> unFetched;

	public LinkCollection() {
		this.fetched = new HashSet<>();
		this.unFetched = new ConcurrentLinkedQueue<String>();
	}

	public boolean isFetched(String link) {
		synchronized (LinkCollection.class) {
			return fetched.contains(link);
		}
	}

	public void setFetched(String url) {
		synchronized (LinkCollection.class) {
			fetched.add(url);
		}
	}

	public String poll() {
		return unFetched.poll();
	}

	public void offer(Set<String> links) {
		for (String link : links) {
			offer(link);
		}
	}

	public void offer(String url) {
		unFetched.offer(url);
	}

	public boolean isQueueEmpty() {
		return unFetched.isEmpty();
	}

	public Set<String> getFetched() {
		return fetched;
	}

	public Queue<String> getUnFetched() {
		return unFetched;
	}

}
