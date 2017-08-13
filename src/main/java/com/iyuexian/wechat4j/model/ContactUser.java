package com.iyuexian.wechat4j.model;

import java.util.List;

/**
 * 会话用户，包括 普通用户，群，公众号，服务号
 * 
 * @author hexia
 *
 */
public class ContactUser {

	public static final int CONTACT_FLAG_OF_FILE_HELPER = 1;
	public static final int CONTACT_FLAG_OF_GROUP = 3;// 群
	public static final int CONTACT_FLAG_OF_USER = 3;// 普通用户

	/****** 如果该值为0 ****/
	private int Uin;
	/****** 用户惟一标识,例如:@84a60bd0094062b1ff98bbbd195035d2 ****/
	private String UserName;
	/***** 用户昵称:例如eno ******/
	private String NickName;
	/***** 头像 **/
	private String HeadImgUrl;
	/****** 未知，一般为1 ****/
	private int ContactFlag; //1-好友， 2-群组， 3-公众号
	/*** 成员数，如果是群，该值应该大于0，如果是用户，该值为0 ***/
	private int MemberCount;
	/**** 群成员列表 ***/
	private List<GroupMember> MemberList;
	/*** 未知 **/
	private String RemarkName;
	/****** 一般为0 **/
	private int HideInputBarFlag;
	/***** 性别 *****/
	private int Sex;
	/*** 签名? **/
	private String Signature;
	/***********/
	private int VerifyFlag;
	/************/
	private int OwnerUin;
	private String PYInitial;

	private String PYQuanPin;

	private String RemarkPYInitial;
	private String RemarkPYQuanPin;
	private int StarFriend;
	private int AppAccountFlag;
	private int Statues;
	private int AttrStatus;
	private String Province;
	private String City;
	private String Alias;
	private int SnsFlag;
	private int UniFriend;
	private String DisplayName;
	private Integer ChatRoomId;
	private String KeyWord;
	private String EncryChatRoomId;
	/***** 是否本人：0：否，1：是 *****/
	private Integer IsOwner;

	public ContactUser() {
	}

	public ContactUser(String userName, String nickName, int memberCount, List<GroupMember> memberList) {
		UserName = userName;
		NickName = nickName;
		MemberCount = memberCount;
		MemberList = memberList;
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

	public String getRemarkName() {
		return RemarkName;
	}

	public int getHideInputBarFlag() {
		return HideInputBarFlag;
	}

	public int getSex() {
		return Sex;
	}

	public String getSignature() {
		return Signature;
	}

	public int getVerifyFlag() {
		return VerifyFlag;
	}

	public int getOwnerUin() {
		return OwnerUin;
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

	public int getStarFriend() {
		return StarFriend;
	}

	public int getAppAccountFlag() {
		return AppAccountFlag;
	}

	public int getStatues() {
		return Statues;
	}

	public int getAttrStatus() {
		return AttrStatus;
	}

	public String getProvince() {
		return Province;
	}

	public String getCity() {
		return City;
	}

	public String getAlias() {
		return Alias;
	}

	public int getSnsFlag() {
		return SnsFlag;
	}

	public int getUniFriend() {
		return UniFriend;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public Integer getChatRoomId() {
		return ChatRoomId;
	}

	public String getKeyWord() {
		return KeyWord;
	}

	public String getEncryChatRoomId() {
		return EncryChatRoomId;
	}

	public Integer getIsOwner() {
		return IsOwner;
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

	public void setRemarkName(String remarkName) {
		RemarkName = remarkName;
	}

	public void setHideInputBarFlag(int hideInputBarFlag) {
		HideInputBarFlag = hideInputBarFlag;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public void setVerifyFlag(int verifyFlag) {
		VerifyFlag = verifyFlag;
	}

	public void setOwnerUin(int ownerUin) {
		OwnerUin = ownerUin;
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

	public void setStarFriend(int starFriend) {
		StarFriend = starFriend;
	}

	public void setAppAccountFlag(int appAccountFlag) {
		AppAccountFlag = appAccountFlag;
	}

	public void setStatues(int statues) {
		Statues = statues;
	}

	public void setAttrStatus(int attrStatus) {
		AttrStatus = attrStatus;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public void setCity(String city) {
		City = city;
	}

	public void setAlias(String alias) {
		Alias = alias;
	}

	public void setSnsFlag(int snsFlag) {
		SnsFlag = snsFlag;
	}

	public void setUniFriend(int uniFriend) {
		UniFriend = uniFriend;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public void setChatRoomId(Integer chatRoomId) {
		ChatRoomId = chatRoomId;
	}

	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}

	public void setEncryChatRoomId(String encryChatRoomId) {
		EncryChatRoomId = encryChatRoomId;
	}

	public void setIsOwner(Integer isOwner) {
		IsOwner = isOwner;
	}

	public List<GroupMember> getMemberList() {
		return MemberList;
	}

	public void setMemberList(List<GroupMember> memberList) {
		MemberList = memberList;
	}

}
