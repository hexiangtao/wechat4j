package com.iyuexian.wechat4j.plugin;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.http.WechatApiUtils;

public class FriendAddTask implements Runnable {
	private long sleepSecond = 5;
	private String chatRoomName = "";
	protected Logger logger = LoggerFactory.getLogger(FriendAddTask.class);
	private WechatMeta meta;

	public void start() {
		new Thread(this).start();
	}

	public FriendAddTask(WechatMeta meta) {
	}

	public FriendAddTask(WechatMeta meta, String chatRoom, long sleepSecond) {
		this.meta = meta;
		this.sleepSecond = sleepSecond;
		this.chatRoomName = chatRoom;
	}

	@Override
	public void run() {
		JSONArray rooms = Storage.instance().getLatestChatRoomList();
		for (int i = 0; i < rooms.size(); i++) {
			JSONObject room = rooms.get(i).asJSONObject();
			logger.info("发现群:【{}】,人数:{}", room.getString("NickName"), room.getInt("MemberCount"));
			if (chatRoomName == null || chatRoomName.trim().length() == 0) {
				continue;
			}
			if (!room.getString("NickName").contains(chatRoomName)) {
				continue;
			}
			addFriend(room);
		}
	}

	private void addFriend(JSONObject room) {
		JSONArray members = room.get("MemberList").asArray();
		for (int j = 0; j < members.size(); j++) {
			JSONObject member = members.get(j).asJSONObject();
			String userName = member.getString("UserName");
			String nickName = member.getString("NickName");
			if (Storage.instance().isFriend(userName)) {
				logger.info("发现您的好友 【{}】,在【{}】群中,忽略", nickName, room.getString("NickName"));
				continue;
			}

			logger.info("开始添加好友 【{}】", nickName);
			WechatApiUtils.addFriend(meta, userName, "nihao");
			long sleep = this.sleepSecond + new Random().nextInt(100);
			logger.info("休眠:{}秒", sleep);
			try {
				Thread.sleep(sleep * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public long getSleepSecond() {
		return sleepSecond;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setSleepSecond(long sleepSecond) {
		this.sleepSecond = sleepSecond;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

}
