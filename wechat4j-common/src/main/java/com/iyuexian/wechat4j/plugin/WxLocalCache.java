package com.iyuexian.wechat4j.plugin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.core.WechatMeta;

public class WxLocalCache {

	private final String CONTACT = "webwxgetcontact";
	private final String LATEST_CONTACT = "latest_webwxgetcontact";

	private Map<String, CacheItem> cacheInstance;
	private ContactManager contactManager;

	private Logger logger = LoggerFactory.getLogger(WxLocalCache.class);

	private WxLocalCache(WechatMeta meta) {
		super();
		this.cacheInstance = new HashMap<String, CacheItem>();
		this.contactManager = new ContactManager(meta);
	}

	static class Holder {
		static WxLocalCache instance = null;
	}

	public static WxLocalCache instance() {
		if (Holder.instance == null) {
			throw new IllegalStateException("wxLocalCache not init");
		}
		return Holder.instance;

	}

	public static WxLocalCache init(WechatMeta meta) {
		if (Holder.instance != null) {
			return Holder.instance;
		}
		Holder.instance = new WxLocalCache(meta);
		return Holder.instance;
	}

	public JSONArray getContactList() {
		JSONArray arr = get(CONTACT);
		if (arr == null && (arr = contactManager.getContactList()) == null) {
			logger.error("获取联系人失败");
			return null;
		}
		return arr;
	}

	public JSONObject getContactByRemarkName(String remarkName) {
		return getContactByName("RemarkName", remarkName);
	}

	public JSONObject getContactByNickName(String nickName) {
		return getContactByName("NickName", nickName);
	}

	public JSONObject getContactByUserName(String ContactName) {
		return getContactByName("UserName", ContactName);
	}

	private JSONObject getContactByName(String fieldName, String fieldVal) {
		JSONArray arr = getContactList();
		if (arr == null) {
			return null;
		}

		for (int i = 0; i < arr.size(); i++) {
			JSONObject val = arr.get(i).asJSONObject();
			String userName = val.getString("UserName");
			String nickName = val.getString("NickName");
			String remarkName = val.getString("RemarkName");
			if (fieldName.equals("UserName") && userName.equals(fieldVal)) {
				return val;
			} else if (fieldName.equals("NickName") && nickName.equals(fieldVal)) {
				return val;
			} else if (fieldName.equals("RemarkName") && remarkName.equals(fieldVal)) {
				return val;
			}
		}
		logger.error("can't find the contact of  " + fieldName + " named  " + fieldName);
		return null;

	}

	public void put(String key, Object data) {
		CacheItem item = new CacheItem(key, data);
		cacheInstance.put(key, item);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		CacheItem item = cacheInstance.get(key);
		if (item == null) {
			return null;
		}
		return (T) item.getData();

	}

	public static class CacheItem {

		private String key;
		private Object data;
		private long timestamp;

		public CacheItem(String key, Object data) {
			this.key = key;
			this.data = data;
			this.timestamp = System.currentTimeMillis();

		}

		public String getKey() {
			return key;
		}

		public Object getData() {
			return data;
		}

		public long getTimestamp() {
			return timestamp;
		}
	}

	public JSONArray getLatestContactList() {
		return get(LATEST_CONTACT);
	}
	
	

	public void setLatestContactList(JSONArray contactList) {
		put(LATEST_CONTACT, contactList);

		for (int i = 0; i < contactList.size(); i++) {
			JSONObject item = contactList.get(i).asJSONObject();
			logger.info("初始化最新联系人 ,item:{}", item.getString("NickName"));
			String userName = item.getString("UserName");
			// 最近联系的群
			if (userName.startsWith("@@")) {
				Storage.instance().addLasetChatroomUserName(userName);
				continue;
			}
			// TODO 最近联系的用户
		}

	}
}