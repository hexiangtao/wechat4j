package com.iyuexian.wechat4j.message;

public enum MsgType {
	MEDIA(-1,"媒体消息","ebwxgetvideo"),
	TXT(1,"文本消息",""),
	PICTURE(3,"图片消息","webwxgetmsgimg"),
	VOICE(34,"语音消息","webwxgetvoice"),
	FRIEND_VERIFY(37,"好友确认消息",""),
	POSSIBLEFRIEND_MSG(40,"POSSIBLEFRIEND_MSG",""),
	SHARE_MP(42,"共享名片",""),
	VIDEO(43,"视频消息","webwxgetvideo"),
	ANIMATION(47,"动画消息",""),
	LOCATION(48,"位置消息",""),
	SHARE_LINK(49,"分享链接",""),
	VOIPMSG(50,"VOIPMSG",""),
	WX_INIT(51,"微信初始化消息",""),
	VOIPNOTIFY(52,"VOIPNOTIFY",""),
	VOIPINVITE(53,"VOIPINVITE",""),
	SMALL_VIDEO(62,"小视频","webwxgetvideo"),
	SYSNOTICE(9999,"SYSNOTICE",""),
	SYSTEM(10000,"","系统消息E"),
	MSG_ROLLBACK(10002,"撤回消息","");


	private int type;
	private String  name;
	private String downloadPath;

	private MsgType(int type,String name, String downloadPath) {
		this.type = type;
		this.downloadPath = downloadPath;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public static MsgType getType(int type) {
		MsgType[] types = MsgType.values();
		for (MsgType msgType : types) {
			if (msgType.getType() == type) {
				return msgType;
			}
		}
		return null;
	}

}