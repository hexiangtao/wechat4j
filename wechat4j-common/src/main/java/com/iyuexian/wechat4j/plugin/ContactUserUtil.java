package com.iyuexian.wechat4j.plugin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.bean.ChatRoom;
import com.iyuexian.wechat4j.bean.ChatRoomMember;
import com.iyuexian.wechat4j.bean.GroupMember;
import com.iyuexian.wechat4j.config.Constant;

public class ContactUserUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(ContactUserUtil.class);

	@Deprecated
	public static List<GroupMember> processMemberJSON(JSONArray memberList) {
		List<GroupMember> members = new ArrayList<GroupMember>();
		for (int i = 0; i < memberList.size(); i++) {
			JSONObject member = memberList.get(i).asJSONObject();
			GroupMember item = new GroupMember(member);
			members.add(item);
		}
		return members;
	}

	public static List<ChatRoomMember> processChatRoomMember(JSONArray memberList) {
		List<ChatRoomMember> members = new ArrayList<ChatRoomMember>();
		for (int i = 0; i < memberList.size(); i++) {
			JSONObject member = memberList.get(i).asJSONObject();
			ChatRoomMember item = new ChatRoomMember(member);
			if (Constant.PRINT_MEMBER_INFO) {
				LOGGER.warn("当前群成员:{}", item.getNickName());
			}
			members.add(item);
		}
		return members;
	}

	public static List<ChatRoom> processChatRoom(JSONArray chatrooms) {

		List<ChatRoom> roomList = new ArrayList<ChatRoom>();
		for (int i = 0; i < chatrooms.size(); i++) {
			JSONObject item = chatrooms.get(i).asJSONObject();
			ChatRoom chatRoom = ChatRoom.parseFromJSON(item);
			LOGGER.warn("当前群:{},人数:{}", chatRoom.getNickName(), chatRoom.getMemberCount());
			JSONArray memberArray = item.get("MemberList").asArray();
			List<ChatRoomMember> memberList = processChatRoomMember(memberArray);
			chatRoom.setMemberList(memberList);
			roomList.add(chatRoom);
		}
		return roomList;

	}

}
