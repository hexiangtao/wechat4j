package com.iyuexian.wechat4j.plugin;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.core.WechatApiUtil;
import com.iyuexian.wechat4j.core.WechatMeta;
import com.iyuexian.wechat4j.plugin.message.MessageManager;

public class MessageListener implements Runnable {

	private Logger logger = LoggerFactory.getLogger(MessageListener.class);
	private WechatMeta meta;
	private MessageManager messageManager;

	public MessageListener(WechatMeta meta) {
		this.meta = meta;
		messageManager = new MessageManager(meta);
	}

	public void listen() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		WechatApiUtil.choiceSyncLine(meta);
		while (true) {
			int[] arr = WechatApiUtil.syncCheck(meta); // 消息检查
			logger.debug("正在监听消息,retcode={}, selector={}", arr[0], arr[1]);

			// retcode: 0 正常 1100 失败/登出微信 selector: 0 正常 2 新的消息 7 进入/离开聊天界面 ```
			int retcode = arr[0], selector = arr[1];

			if (retcode == 1100 || retcode == 1101) {
				logger.warn("用户微信退出");
				break;
			}
			if (retcode != 0) {
				sleep(2);
				continue;
			}
			if (selector == 7) {
				logger.debug("进入(离开)聊天界面");
			} else if (selector == 0 || selector == 3) {
				continue;
			} else if (selector == 2 || selector == 6) {
				handleMsg();
			}
			sleep(4);

		}

	}

	private void handleMsg() {
		try {
			JSONObject data = WechatApiUtil.webwxsync(meta);
			messageManager.process(data);
		} catch (Exception ex) {
			logger.error("处理消息异常,ex:{}", ex);
		}

	}

	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
