package com.iyuexian.wechat4j.model;

import com.blade.kit.json.JSONObject;

public class GroupMember {

	private Integer Uin;
	private String UserName;
	private String NickName;
	private Integer AttrStatus;
	private String PYInitial;
	private String PYQuanPin;
	private String RemarkPYInitial;
	private String RemarkPYQuanPin;
	private Integer MemberStatus; // 0
	private String DisplayName;
	private String KeyWord;

	public GroupMember() {
	}

	public GroupMember(Integer uin, String userName, String nickName, Integer attrStatus, String pYInitial, String pYQuanPin, String remarkPYInitial,
			String remarkPYQuanPin, Integer memberStatus, String displayName, String keyWord) {
		this.Uin = uin;
		this.UserName = userName;
		this.NickName = nickName;
		this.AttrStatus = attrStatus;
		this.PYInitial = pYInitial;
		this.PYQuanPin = pYQuanPin;
		this.RemarkPYInitial = remarkPYInitial;
		this.RemarkPYQuanPin = remarkPYQuanPin;
		this.MemberStatus = memberStatus;
		this.DisplayName = displayName;
		this.KeyWord = keyWord;
	}

	public GroupMember(JSONObject item) {
		this.Uin = item.getInt("Uin");
		this.UserName = item.getString("UserName");
		this.NickName = item.getString("NickName");
		this.AttrStatus = item.getInt("AttrStatus");
		this.PYInitial = item.getString("PYInitial");
		this.PYQuanPin = item.getString("PYQuanPin");
		this.RemarkPYInitial = item.getString("RemarkPYInitial");
		this.RemarkPYQuanPin = item.getString("RemarkPYQuanPin");
		this.MemberStatus = item.getInt("MemberStatus");
		this.DisplayName = item.getString("DisplayName");
		this.KeyWord = item.getString("KeyWord");
	}

	public Integer getUin() {
		return Uin;
	}

	public String getUserName() {
		return UserName;
	}

	public String getNickName() {
		return NickName;
	}

	public Integer getAttrStatus() {
		return AttrStatus;
	}

	public String getPYInitial() {
		return PYInitial;
	}

	public String getPYQuanPin() {
		return PYQuanPin;
	}

	public String getRemarkPYInitial() {
		return RemarkPYInitial;
	}

	public String getRemarkPYQuanPin() {
		return RemarkPYQuanPin;
	}

	public Integer getMemberStatus() {
		return MemberStatus;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public String getKeyWord() {
		return KeyWord;
	}

	public void setUin(Integer uin) {
		Uin = uin;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public void setAttrStatus(Integer attrStatus) {
		AttrStatus = attrStatus;
	}

	public void setPYInitial(String pYInitial) {
		PYInitial = pYInitial;
	}

	public void setPYQuanPin(String pYQuanPin) {
		PYQuanPin = pYQuanPin;
	}

	public void setRemarkPYInitial(String remarkPYInitial) {
		RemarkPYInitial = remarkPYInitial;
	}

	public void setRemarkPYQuanPin(String remarkPYQuanPin) {
		RemarkPYQuanPin = remarkPYQuanPin;
	}

	public void setMemberStatus(Integer memberStatus) {
		MemberStatus = memberStatus;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}

	@Override
	public String toString() {
		return "GroupMember [Uin=" + Uin + ", UserName=" + UserName + ", NickName=" + NickName + ", AttrStatus=" + AttrStatus + ", PYInitial="
				+ PYInitial + ", PYQuanPin=" + PYQuanPin + ", RemarkPYInitial=" + RemarkPYInitial + ", RemarkPYQuanPin=" + RemarkPYQuanPin
				+ ", MemberStatus=" + MemberStatus + ", DisplayName=" + DisplayName + ", KeyWord=" + KeyWord + "]";
	}

}
