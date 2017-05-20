package com.qiluomite.mywechat.message;

public enum MsgType {
	TXT(1), WxInit(51), VOICE(34), MP(42), PICTURE(3);

	private int type;

	private MsgType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
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