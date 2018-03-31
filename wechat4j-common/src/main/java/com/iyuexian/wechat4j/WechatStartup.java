package com.iyuexian.wechat4j;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.core.WechatApiUtil;
import com.iyuexian.wechat4j.core.WechatMeta;
import com.iyuexian.wechat4j.exception.WechatException;
import com.iyuexian.wechat4j.plugin.QRCodeWindow;
import com.iyuexian.wechat4j.plugin.WxLocalCache;
import com.iyuexian.wechat4j.util.Matchers;

public class WechatStartup {

	protected Logger logger = LoggerFactory.getLogger(WechatStartup.class);
	private QRCodeWindow qrCodeFrame;
	private WechatMeta meta;

	private WechatStartup() {
		System.setProperty("https.protocols", "TLSv1");
		System.setProperty("jsse.enableSNIExtension", "false");
	}

	public static WechatMeta login() {
		try {
			WechatStartup instance = new WechatStartup();
			instance.doLogin();
			return instance.meta;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	private void doLogin() throws Exception {
		String uuid = WechatApiUtil.getUUID();
		showQrCode(uuid);

		while (true) {

			String res = WechatApiUtil.waitLogin(0, uuid);
			String code = Matchers.match("window.code=(\\d+);", res);

			if (!Constant.HTTP_OK.equals(code)) {
				Thread.sleep(2000);
				continue;
			}
			closeQrWindow();
			WechatMeta meta = WechatApiUtil.newWechatMeta(res);
			WechatApiUtil.login(meta);
			JSONObject wxInitObj = WechatApiUtil.wxInit(meta);
			WechatApiUtil.openStatusNotify(meta);
			this.meta = meta;
			JSONArray contactList = wxInitObj.get("ContactList").asArray();
			WxLocalCache.init(this.meta).setLatestContactList(contactList);
			break;
		}
	}

	private void showQrCode(String uuid) throws WechatException {

		final String path = WechatApiUtil.getQrCode(uuid);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					qrCodeFrame = new QRCodeWindow(path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void closeQrWindow() {
		qrCodeFrame.dispose();
	}

	public WechatMeta getMeta() {
		return meta;
	}

}
