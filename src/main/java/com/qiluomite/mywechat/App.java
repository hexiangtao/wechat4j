package com.qiluomite.mywechat;

import com.qiluomite.mywechat.client.MessageListener;
import com.qiluomite.mywechat.component.WechatCore;

public class App extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		MessageListener t1 = new MessageListener(app);
		app.run(t1);

	}

}