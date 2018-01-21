package com.iyuexian.wechat4j.config;

import java.util.Arrays;
import java.util.List;

import com.iyuexian.wechat4j.util.PropertyReader;

public class Constant {

	// 特殊用户 须过滤
	public static final List<String> FILTER_USERS = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail", "fmessage", "tmessage",
			"qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp",
			"meishiapp", "feedsapp", "voip", "blogappweixin", "weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11",
			"gh_22b87fa7cb3c", "officialaccounts", "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil",
			"userexperience_alarm", "notification_messages");

	public static final String[] SYNC_HOST = { "webpush.weixin.qq.com", "webpush2.weixin.qq.com", "webpush.wechat.com", "webpush1.wechat.com",
			"webpush2.wechat.com" };

	public static final String QINIU_AK = "qiniu_ak";
	/**** 七牛access secret ***/
	public static final String QINIU_SK = "qiniu_sk";
	/**** 图片域名地址 ***/
	public static final String QIUNIU_IMG_URL = "qiuniu_img_url";
	/****** 视频域名地址 *****/
	public static final String QIUNIU_VDEO_URL = "qiuniu_video_url";
	/**** 七牛图片bucket名称 ****/
	public static final String QINIU_BUCKET_IMAGE0 = "qiniu_bucket_image0";
	/**** 七牛视频bucket名称 ****/
	public static final String QINIU_BUCKET_VIDEO = "qiniu_bucket_video";
	
	
	/***** 是否开启自动回复 ******/
	public static final boolean AUTO_REPLY = true;
	/***** 是否打印成员信息 ****/
	public static final boolean PRINT_MEMBER_INFO = false;
	/****** 是否开启自动添加好友 ******/
	public static final boolean ADD_FRIEND = false;

	public static final String HTTP_OK = "200";
	public static final String BASE_URL = "https://webpush2.weixin.qq.com/cgi-bin/mmwebwx-bin";
	public static final String JS_LOGIN_URL = "https://login.weixin.qq.com/jslogin";
	public static final String QRCODE_URL = "https://login.weixin.qq.com/qrcode/";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
	public static PropertyReader configReader = PropertyReader.load("config.properties");

}
