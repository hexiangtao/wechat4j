package com.iyuexian.wechat4j.model;

import java.util.List;

import com.blade.kit.json.JSONObject;

public class ChatRoom {

	private int Uin;
	private String UserName;
	private String NickName;
	private String HeadImgUrl;
	private int ContactFlag;
	private int MemberCount;
	private List<ChatRoomMember> MemberList;

	public ChatRoom() {
	}

	public static  ChatRoom parseFromJSON(JSONObject data) {
		ChatRoom chatroom = new ChatRoom();
		chatroom.Uin = data.getInt("Uin");
		chatroom.UserName = data.getString("UserName");
		chatroom.NickName = data.getString("NickName");
		chatroom.HeadImgUrl = data.getString("HeadImgUrl");
		chatroom.ContactFlag = data.getInt("ContactFlag");
		chatroom.MemberCount = data.getInt("MemberCount");
		return chatroom;
	}

	public ChatRoom(int uin, String userName, String nickName, String headImgUrl, int contactFlag, int memberCount) {
		super();
		Uin = uin;
		UserName = userName;
		NickName = nickName;
		HeadImgUrl = headImgUrl;
		ContactFlag = contactFlag;
		MemberCount = memberCount;
	}

	public int getUin() {
		return Uin;
	}

	public String getUserName() {
		return UserName;
	}

	public String getNickName() {
		return NickName;
	}

	public String getHeadImgUrl() {
		return HeadImgUrl;
	}

	public int getContactFlag() {
		return ContactFlag;
	}

	public int getMemberCount() {
		return MemberCount;
	}

	public List<ChatRoomMember> getMemberList() {
		return MemberList;
	}

	public void setUin(int uin) {
		Uin = uin;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public void setHeadImgUrl(String headImgUrl) {
		HeadImgUrl = headImgUrl;
	}

	public void setContactFlag(int contactFlag) {
		ContactFlag = contactFlag;
	}

	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	public void setMemberList(List<ChatRoomMember> memberList) {
		MemberList = memberList;
	}

}
