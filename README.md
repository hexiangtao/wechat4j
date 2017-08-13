# wechat4j





##  程序入口

````
package com.iyuexian.wechat4j;

import com.iyuexian.wechat4j.core.WechatCore;

public class App extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.run();

	}

}

````


您可以使用自定义类继承WechatCore,添加自己需要的组件，例如下面代码添加了一个MessageListener，监听微信消息
````
package com.iyuexian.wechat4j;
import com.iyuexian.wechat4j.core.WechatCore;
import com.iyuexian.wechat4j.plugin.MessageListener;

public class  MyClass extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		MessageListener t1 = new MessageListener(app);
		app.run(t1);

	}

}
````

## 默认未开启机器人功能，您可以在Config类打开如下设置
````
	/*****是否开启自动回复******/
	public static final boolean AUTO_REPLY = false;
	/*****是否打印成员信息****/
	public static final boolean PRINT_MEMBER_INFO = true;
	/******是否开启自动添加好友******/
	public static final boolean ADD_FRIEND = false;

`````

## 配置文件说明:

````
###机器人接入密钥######
chat.key=7507257e2872417d9e4fb0e2764cf0ac
chat.secret=36c3be794b4950dc
chat.server_url=http://www.tuling123.com/openapi/api

######消息本地存储路径####
app.media_path=C:/wechat
app.msg_location=C:/wechat/msg.txt

````


程序包结构,如下 </br>
![Alt text](https://github.com/enohe/wechat4j/blob/master/%E5%8C%85%E7%BB%93%E6%9E%84.png)

### 包结构说明:
#####  com.iyuexian.wechat4j    入口类
##### com.iyuexian.wechat4j.config  配置信息及系统常量 
##### com.iyuexian.wechat4j.conre   核心功能实现
##### com.iyuexian.wechat4j.message   各种消息处理
##### com.iyuexian.wechat4j.model     定义的各种实体类，比如，好友,微信群...




####### WechatCore类启动步骤
````
package com.iyuexian.wechat4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iyuexian.wechat4j.config.Config;
import com.iyuexian.wechat4j.exception.WechatException;
import com.iyuexian.wechat4j.message.FileHandler;
import com.iyuexian.wechat4j.model.WechatMeta;

public abstract class WechatCore extends WechatMeta {

	protected Logger logger = LoggerFactory.getLogger(WechatCore.class);

	private LoginHandler loginHandler;
	private ContactHandler contactHandler;
	private MessageHandler messageHandler;
	private FileHandler fileHandler;
	private List<Task> userTasks = new ArrayList<Task>();

	public WechatCore() {
		super("classpath:config.properties");
		System.setProperty("https.protocols", "TLSv1");
		System.setProperty("jsse.enableSNIExtension", "false");
		this.loginHandler = new LoginHandler(this);
		this.contactHandler = new ContactHandler(this);
		this.messageHandler = new MessageHandler(this);
		this.fileHandler = new FileHandler(this);
	}

	public void run(Task... tasks) throws InterruptedException {
		loginHandler.getUUID();
		loginHandler.showQrCode();
		while (!Config.HTTP_OK.equals(loginHandler.waitForLogin())) {
			Thread.sleep(2000);
		}
		loginHandler.closeQrWindow();
		doInit();
		if (tasks != null && tasks.length > 0) {
			this.userTasks.addAll(Arrays.asList(tasks));
		}
		beginUserTask();
	}

	private void doInit() throws WechatException {
		loginHandler.login();
		loginHandler.wxInit();

		loginHandler.openStatusNotify();
		contactHandler.initLatestChatroom();
		contactHandler.initContactList();

	}

	private void beginUserTask() {
		doUserTask(userTasks);
	}

	private void doUserTask(List<Task> tasks) {
		for (Task task : tasks) {
			if (task == null) {
				continue;
			}
			try {
				task.start();
			} catch (Exception ex) {
				logger.warn("start task error.{}", ex);
			}
		}

	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}

	public ContactHandler getContactHandler() {
		return contactHandler;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

}

````

####### 在run方法中依次调用如下方法
````
geetUUID()       获取微信接口获取UUID
showQrcode()	 显示二维码,待用户扫码登陆		
beginUserTask()   开始用户自定义的任务
`````








