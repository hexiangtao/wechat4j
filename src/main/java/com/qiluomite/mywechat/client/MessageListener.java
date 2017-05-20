package com.qiluomite.mywechat.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.component.Task;
import com.qiluomite.mywechat.component.WechatCore;

public class MessageListener extends Task {

	private  Logger logger = LoggerFactory.getLogger(MessageListener.class);

	private WechatCore core;

	int playWeChat = 0;

	public MessageListener(WechatCore core) {
		this.core = core;
	}

	@Override
	public void initTask() {
		logger.info("进入消息监听模式 ...");
		core.getLoginHandler().choiceSyncLine();
		while (true) {
			int[] arr = core.getLoginHandler().syncCheck();
			logger.debug("retcode={}, selector={}", arr[0], arr[1]);

			if (arr[0] == 1100) {
				logger.info("你在手机上登出了微信，债见");
				break;
			}
			if (arr[0] == 0) {
				if (arr[1] == 2) {
					JSONObject data = core.getLoginHandler().webwxsync();
					// System.out.println(data.toString());
					core.getMessageHandler().handleMsg(data);
				} else if (arr[1] == 6) {
					JSONObject data = core.getLoginHandler().webwxsync();
					core.getMessageHandler().handleMsg(data);
				} else if (arr[1] == 7) {
					playWeChat += 1;
					logger.info("你在手机上玩微信被我发现了 {} 次", playWeChat);
					core.getLoginHandler().webwxsync();
				} else if (arr[1] == 3) {
					continue;
				} else if (arr[1] == 0) {
					continue;
				}
			} else {
			}
			try {
				Thread.sleep(4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
