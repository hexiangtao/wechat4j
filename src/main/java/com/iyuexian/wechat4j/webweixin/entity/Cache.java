package com.iyuexian.wechat4j.webweixin.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	private Map<String, CacheItem> map = new ConcurrentHashMap<String, CacheItem>();

	public CacheItem put(String key, CacheItem item) {
		CacheItem oldItem = map.get(key);
		map.put(key, item);
		return oldItem;
	}

	public boolean exists(String key) {
		CacheItem cacheItem = map.get(key);
		if (cacheItem == null) {
			return false;
		}
		if (cacheItem.getPersistSeconds() == 0) {
			return true;
		}
		if (cacheItem.getLastAccessTime() + cacheItem.getPersistSeconds() * 100 < System.currentTimeMillis()) {
			map.remove(key);
			return false;
		}
		return true;
	}

	public void clearAll() {
		map.clear();
	}

	public boolean del(String key) {
		CacheItem cacheItem = map.get(key);
		if (cacheItem == null) {
			return false;
		}
		map.remove(key);
		return true;
	}

	public CacheItem get(String key) {
		CacheItem cacheItem = map.get(key);
		if (cacheItem == null) {
			return null;
		}
		if (cacheItem.getLastAccessTime() + cacheItem.getPersistSeconds() * 100 < System.currentTimeMillis()) {
			map.remove(key);
			return null;
		}
		cacheItem.setLastAccessTime(System.currentTimeMillis());
		return cacheItem;
	}

}
