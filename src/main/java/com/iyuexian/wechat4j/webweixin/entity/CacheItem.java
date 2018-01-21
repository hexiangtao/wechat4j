package com.iyuexian.wechat4j.webweixin.entity;

public class CacheItem {

	private String key;
	private String value;
	private long lastAccessTime;
	private int persistSeconds; // 缓存时长 0:表示永久

	public CacheItem(String key, String value, long lastAccessTime, int persistSeconds) {
		this.key = key;
		this.value = value;
		this.lastAccessTime = lastAccessTime;
		this.persistSeconds = persistSeconds;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getPersistSeconds() {
		return persistSeconds;
	}

	public void setPersistSeconds(int persistSeconds) {
		this.persistSeconds = persistSeconds;
	}

}
