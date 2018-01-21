package com.iyuexian.wechat4j;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.plugin.QRCodeWindow;
import com.iyuexian.wechat4j.plugin.Storage;
import com.iyuexian.wechat4j.util.Matchers;
import com.iyuexian.wechat4j.webweixin.WechatApiUtils;

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
		String uuid = WechatApiUtils.getUUID();
		showQrCode(uuid);

		while (true) {

			String res = WechatApiUtils.waitLogin(0, uuid);
			String code = Matchers.match("window.code=(\\d+);", res);

			if (!Constant.HTTP_OK.equals(code)) {
				Thread.sleep(2000);
				continue;
			}
			closeQrWindow();
			WechatMeta meta = WechatApiUtils.newWechatMeta(res);
			WechatApiUtils.login(meta);
			JSONObject wxInitObj = WechatApiUtils.wxInit(meta);
			initLatestContactUser(wxInitObj);
			WechatApiUtils.openStatusNotify(meta);
			this.meta = meta;
			break;
		}
	}

	private void showQrCode(String uuid) throws WechatException {

		final String path = WechatApiUtils.getQrCode(uuid);
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

	public void initLatestContactUser(JSONObject wxInitObj) {

		JSONArray contactList = wxInitObj.get("ContactList").asArray();
		for (int i = 0; i < contactList.size(); i++) {
			JSONObject item = contactList.get(i).asJSONObject();
			logger.info("load user,item:{}", item.getString("NickName"));
			String userName = item.getString("UserName");
			// 最近联系的群
			if (userName.startsWith("@@")) {
				Storage.instance().addLasetChatroomUserName(userName);
				continue;
			}
			// TODO 最近联系的用户
		}
	}

	private void closeQrWindow() {
		qrCodeFrame.dispose();
	}

	public WechatMeta getMeta() {
		return meta;
	}

}
