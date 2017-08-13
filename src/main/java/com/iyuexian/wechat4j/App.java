package com.iyuexian.wechat4j;

import com.iyuexian.wechat4j.core.WechatCore;
import com.iyuexian.wechat4j.plugin.MessageListener;

public class App extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		MessageListener t1 = new MessageListener(app);
		app.run(t1);

	}

}