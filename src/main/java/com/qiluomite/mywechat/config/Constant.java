package com.qiluomite.mywechat.config;

import java.util.Arrays;
import java.util.List;

public class Constant {

	// 特殊用户 须过滤
	public static final List<String> FILTER_USERS = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail", "fmessage", "tmessage",
			"qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp",
			"meishiapp", "feedsapp", "voip", "blogappweixin", "weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11",
			"gh_22b87fa7cb3c", "officialaccounts", "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil",
			"userexperience_alarm", "notification_messages");

	public static final String[] SYNC_HOST = { "webpush.weixin.qq.com", "webpush2.weixin.qq.com", "webpush.wechat.com", "webpush1.wechat.com",
			"webpush2.wechat.com", "webpush1.wechatapp.com" };

	

}
