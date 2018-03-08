package com.iyuexian.wechat4j.demo;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.WechatStartup;
import com.iyuexian.wechat4j.http.WechatApiUtils;
import com.iyuexian.wechat4j.plugin.FriendAddTask;
import com.iyuexian.wechat4j.plugin.MessageListener;
import com.iyuexian.wechat4j.plugin.WxLocalCache;

public class Demo1 {

	public static void main(String[] args) {
		test3();
	}

	// 给指定人发消息
	public static void test1() throws InterruptedException {
		WechatMeta meta = WechatStartup.login();
		JSONObject contact = WxLocalCache.instance().getContactByRemarkName("Test");
		if (contact == null) {
			return;
		}
		WechatApiUtils.webwxsendmsg(meta, "哈哈", contact);
	}

	// 给所有联系人发消息
	public static void test2() {
		WechatMeta meta = WechatStartup.login();
		JSONArray contactList = WxLocalCache.instance().getContactList();
		for (int i = 0; i < contactList.size(); i++) {
			WechatApiUtils.webwxsendmsg(meta, "^^", contactList.get(i).asJSONObject());
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

}