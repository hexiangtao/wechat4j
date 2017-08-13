package com.iyuexian.wechat4j.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.config.Constant;

public class Storage {

	public static final Logger LOGGER = LoggerFactory.getLogger(Storage.class);

	private List<String> LATEST_GROUP_USERNAME_LIST = new ArrayList<String>();
	private JSONArray latestChatRoomList;

	private JSONArray allContact = new JSONArray();
	private JSONArray chatRoomList = new JSONArray();
	private JSONArray mpList = new JSONArray();
	private JSONArray specailList = new JSONArray();
	private JSONArray friendList = new JSONArray();

	private static Storage instance = null;

	private Storage() {
	};

	public synchronized static Storage instance() {
		if (instance != null)
			return instance;
		instance = new Storage();
		return instance;

	}

	public void addLasetChatroomUserName(String userName) {
		this.LATEST_GROUP_USERNAME_LIST.add(userName);
	}

	public void loadMemberList(JSONArray all) {

		this.allContact = all;
		if (all != null) {
			parseMemberList(allContact);
		}

	}

	private void clear() {
		this.mpList = new JSONArray();
		this.specailList = new JSONArray();
		this.chatRoomList = new JSONArray();

	}

	private void parseMemberList(JSONArray contactList) {
		clear();
		for (int i = 0; i < contactList.size(); i++) {
			JSONObject item = contactList.get(i).asJSONObject();
			if (item.getInt("VerifyFlag", 0) == 8) {
				mpList.add(item);
			} else if (Constant.FILTER_USERS.contains(item.getString("UserName"))) {
				specailList.add(item);

			} else if (item.getString("UserName").indexOf("@@") != -1) {
				chatRoomList.add(item);

			} else {
				friendList.add(item);

			}
		}
		LOGGER.info("初始化联系人完成,当前共加载{}个联系人,公众号:{}个,好友:{}个,群:{}个", allContact.size(), mpList.size(), friendList.size(), chatRoomList.size());
	}

	public boolean isFriend(String userName) {
		for (int i = 0; i < friendList.size(); i++) {
			JSONObject member = friendList.get(i).asJSONObject();
			if (member.getString("UserName").equals(userName)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getLatestChatRoomUserNameList() {
		return this.LATEST_GROUP_USERNAME_LIST;
	}

	public JSONArray getAllContact() {
		return allContact;
	}

	public JSONArray getChatRoomList() {
		return chatRoomList;
	}

	public JSONArray getMpList() {
		return mpList;
	}

	public JSONArray getSpecailList() {
		return specailList;
	}

	public JSONArray getFriendList() {
		return friendList;
	}

	public JSONArray getLatestChatRoomList() {
		return latestChatRoomList;
	}

	public void setLatestChatRoomList(JSONArray latestChatRoomList) {
		this.latestChatRoomList = latestChatRoomList;
	}

}
