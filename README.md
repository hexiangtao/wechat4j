# wechat4j
用java实现的微信客户端，支持自动聊天, 消息监听，自动回复，添加好友，获取群成员列表,自动记录聊天记录，自动下载图片，语音，视频消息

# 启动方式
###  1:控制台启动
    自定任意类，在main方法下用WecahtStartup.login()实现扫码登陆
```
WechatMeta meta = WechatStartup.login();	
```
登陆成功后，会返回WechatMeta实例，里面包含了当前扫码用户的登陆信息，后面所有的功能都需要该实例
WechatApiUtils类封装了web微信的相关接口，具体功能实现在plugin及其实子包里，您也可以在这基础上扩展


## 2:以web方式启动，需要在config.properties文件里配置mysql的帐号密码


#### 代码示例
````
	// 给指定人发消息
	public static void test1() throws InterruptedException {
		WechatMeta meta = WechatStartup.login();
		JSONObject contact = WxLocalCache.instance().getContactByRemarkName("Test");
		if (contact == null) {
			return;
		}
		WechatApiUtil.webwxsendmsg(meta, "哈哈", contact);
	}

	// 给所有联系人发消息
	public static void test2() {
		WechatMeta meta = WechatStartup.login();
		JSONArray contactList = WxLocalCache.instance().getContactList();
		for (int i = 0; i < contactList.size(); i++) {
			WechatApiUtil.webwxsendmsg(meta, "^^", contactList.get(i).asJSONObject());
		}
	}

	// 监听消息
	public static void test3() {
		WechatMeta meta = WechatStartup.login();
		MessageListener listener = new MessageListener(meta);
		listener.listen();
	}

	// 批量添加群里的人(该方法不稳定，没控制好间隔时间很容易被微信服务器察觉，返回失败)
	public static void batchAddChatRoomFriend() {
		WechatMeta meta = WechatStartup.login();
		FriendAddTask task = new FriendAddTask(meta, "添加添加好友的群名称", 15);
		task.start();
	} 
 
````


QQ交流群:580889921


