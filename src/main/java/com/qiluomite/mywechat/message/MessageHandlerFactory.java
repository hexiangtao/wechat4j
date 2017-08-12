package com.qiluomite.mywechat.message;

import java.util.HashMap;
import java.util.Map;

import com.qiluomite.mywechat.bean.WechatMeta;

public class MessageHandlerFactory {
	private WechatMeta meta;

	private Map<MsgType, IMessageHandler> handlers = new HashMap<MsgType, IMessageHandler>();

	public MessageHandlerFactory(WechatMeta meta) {
		this.meta = meta;
		register();
	}

	public IMessageHandler getMessageHandler(int type) {
		MsgType msgType = MsgType.getType(type);
		if (msgType == null)
			return handlers.get(MsgType.TXT);
		return handlers.get(msgType);

	}

	private void register() {
		handlers.put(MsgType.TXT, new TxtMessageHandler(meta));
		handlers.put(MsgType.TXT, new TxtMessageHandler(meta));
		handlers.put(MsgType.VOICE, new VoiceMessageHandler(meta));
		handlers.put(MsgType.PICTURE, new PictureMessageHandler(meta));
		handlers.put(MsgType.SHARE_MP, new MpMessageHandler(meta));
		VideoMessageHandler videoMessageHandler = new VideoMessageHandler(meta);
		handlers.put(MsgType.SMALL_VIDEO, videoMessageHandler);
		handlers.put(MsgType.VIDEO, videoMessageHandler);
	}

}
