package com.qiluomite.mywechat.component;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONKit;
import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.config.Config;
import com.qiluomite.mywechat.config.Constant;
import com.qiluomite.mywechat.exception.WechatException;
import com.qiluomite.mywechat.util.CookieUtil;
import com.qiluomite.mywechat.util.HttpRequsetUtil;
import com.qiluomite.mywechat.util.Matchers;

public class LoginHandler {
	private WechatMeta meta;
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);

	private QRCodeFrame qrCodeFrame;

	public LoginHandler(WechatMeta wechatMeta) {
		super();
		this.meta = wechatMeta;
	}

	/**
	 * 获取UUID
	 */
	public String getUUID() throws WechatException {
		HttpRequest request = HttpRequest.get(Config.JS_LOGIN_URL, true, "appid", "wx782c26e4c19acffb", "fun", "new", "lang", "zh_CN", "_",
				DateKit.getCurrentUnixTime());
		String res = request.body();
		request.disconnect();
		if (StringKit.isBlank(res)) {
			throw new WechatException("获取UUID失败");
		}
		String code = Matchers.match("window.QRLogin.code = (\\d+);", res);
		if (null == code || !code.equals("200")) {
			throw new WechatException("获取UUID失败，" + code);
		}
		String uuid = Matchers.match("window.QRLogin.uuid = \"(.*)\";", res);
		this.meta.setUuid(uuid);
		return uuid;
	}

	/**
	 * 显示二维码
	 * 
	 * @return
	 */
	public void showQrCode() throws WechatException {

		String uuid = this.meta.getUuid();
		if (StringKit.isBlank(uuid)) {
			throw new WechatException("请先获取UUID");
		}
		String url = Config.QRCODE_URL + uuid;
		final File output = new File("temp.jpg");
		HttpRequest.post(url, true, "t", "webwx", "_", DateKit.getCurrentUnixTime()).receive(output);

		if (null == output || !output.exists() || !output.isFile()) {
			throw new WechatException("获取登陆二维码失败");
		}
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				qrCodeFrame = new QRCodeFrame(output.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public void closeQrWindow() {
		qrCodeFrame.dispose();
	}

	public LoginHandler wxInit() throws WechatException {
		String url = meta.getBase_uri() + "/webwxinit?r=" + DateKit.getCurrentUnixTime() + "&lang=zh_CN";
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie())
				.send(body.toString());
		String res = request.body();
		request.disconnect();

		JSONObject jsonObject = null;
		if (StringKit.isBlank(res) || (jsonObject = JSONKit.parseObject(res)) == null) {
			throw new WechatException("微信初始化失败");
		}

		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null == BaseResponse || BaseResponse.getInt("Ret", -1) != 0) {
			throw new WechatException("微信初始化失败," + BaseResponse);
		}

		meta.setSyncKey(jsonObject.get("SyncKey").asJSONObject());
		meta.setUser(jsonObject.get("User").asJSONObject());

		JSONArray concatList = jsonObject.get("ContactList").asArray();
		initLatestContactUser(concatList);

		StringBuffer synckey = new StringBuffer();
		JSONArray list = this.meta.getSyncKey().get("List").asArray();
		for (int i = 0, len = list.size(); i < len; i++) {
			JSONObject item = list.get(i).asJSONObject();
			synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
		}
		this.meta.setSynckey(synckey.substring(1));

		return this;
	}

	public void initLatestContactUser(JSONArray contactList) {
		for (int i = 0; i < contactList.size(); i++) {
			JSONObject item = contactList.get(i).asJSONObject();
			String userName = item.getString("UserName");
			// 最近联系的群
			if (userName.startsWith("@@")) {
				Storage.instance().addLasetChatroomUserName(userName);
				continue;
			}
			// TODO 最近联系的用户
		}
	}

	/**
	 * 登录
	 */
	public LoginHandler login() throws WechatException {

		if (StringKit.isBlank(this.meta.getRedirect_uri())) {
			throw new WechatException("redirect_url不能为空");
		}

		HttpRequest request = HttpRequest.get(this.meta.getRedirect_uri());
		String res = request.body();
		this.meta.setCookie(CookieUtil.getCookie(request));
		request.disconnect();
		if (StringKit.isBlank(res)) {
			throw new WechatException("登录失败");
		}
		this.meta.setSkey(Matchers.match("<skey>(\\S+)</skey>", res));
		this.meta.setWxsid(Matchers.match("<wxsid>(\\S+)</wxsid>", res));
		this.meta.setWxuin(Matchers.match("<wxuin>(\\S+)</wxuin>", res));
		this.meta.setPass_ticket(Matchers.match("<pass_ticket>(\\S+)</pass_ticket>", res));

		JSONObject baseRequest = new JSONObject();
		baseRequest.put("Uin", this.meta.getWxuin());
		baseRequest.put("Sid", this.meta.getWxsid());
		baseRequest.put("Skey", this.meta.getSkey());
		baseRequest.put("DeviceID", this.meta.getDeviceId());
		this.meta.setBaseRequest(baseRequest);
		LOGGER.debug("skey [{}],wxsid [{}],wxuin [{}],pass_ticket [{}]", this.meta.getSkey(), this.meta.getWxsid(), this.meta.getWxuin(),
				this.meta.getPass_ticket());
		File output = new File("temp.jpg");
		if (output.exists()) {
			output.delete();
		}
		return this;

	}

	/**
	 * 开启状态通知
	 * 
	 * @throws WechatException
	 */
	public LoginHandler openStatusNotify() throws WechatException {
		String url = meta.getBase_uri() + "/webwxstatusnotify?lang=zh_CN&pass_ticket=" + meta.getPass_ticket();

		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("Code", 3);
		body.put("FromUserName", meta.getUser().getString("UserName"));
		body.put("ToUserName", meta.getUser().getString("UserName"));
		body.put("ClientMsgId", DateKit.getCurrentUnixTime());

		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie())
				.send(body.toString());
		String res = request.body();
		request.disconnect();

		if (StringKit.isBlank(res)) {
			throw new WechatException("状态通知开启失败");
		}

		JSONObject jsonObject = JSONKit.parseObject(res);
		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null == BaseResponse || BaseResponse.getInt("Ret", -1) != 0) {
			throw new WechatException("状态通知开启失败");
		}
		// LOGGER.warn("状态通知已开启");
		return this;
	}

	private int[] syncCheck(String url) throws WechatException {

		url = url == null ? meta.getWebpush_url() + "/synccheck" : "https://" + url + "/cgi-bin/mmwebwx-bin/synccheck";
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());

		HttpRequest request = HttpRequest
				.get(url, true, "r", DateKit.getCurrentUnixTime() + StringKit.getRandomNumber(5), "skey", meta.getSkey(), "uin", meta.getWxuin(),
						"sid", meta.getWxsid(), "deviceid", meta.getDeviceId(), "synckey", meta.getSynckey(), "_", System.currentTimeMillis())
				.header("Cookie", meta.getCookie());

		LOGGER.debug(request.toString());

		String res = request.body();
		request.disconnect();

		int[] arr = new int[] { -1, -1 };
		if (StringKit.isBlank(res)) {
			return arr;
		}

		String retcode = Matchers.match("retcode:\"(\\d+)\",", res);
		String selector = Matchers.match("selector:\"(\\d+)\"}", res);
		if (null != retcode && null != selector) {
			arr[0] = Integer.parseInt(retcode);
			arr[1] = Integer.parseInt(selector);
			return arr;
		}
		return arr;
	}

	public int[] syncCheck() throws WechatException {
		return this.syncCheck(null);
	}

	/**
	 * 等待登录
	 */
	public String waitForLogin() throws WechatException {
		int tip = 1;
		String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login";
		HttpRequest request = HttpRequest.get(url, true, "tip", tip, "uuid", meta.getUuid(), "_", DateKit.getCurrentUnixTime());
		LOGGER.warn("等待登陆");
		String res = request.body();
		request.disconnect();

		if (null == res) {
			throw new WechatException("扫描二维码验证失败");
		}
		String code = Matchers.match("window.code=(\\d+);", res);
		if (null == code) {
			throw new WechatException("扫描二维码验证失败");
		}
		if (code.equals("201")) {
			 LOGGER.info("成功扫描,请在手机上点击确认以登录");
			tip = 0;
		}

		if (code.equals("200")) {
			LOGGER.info("正在登录...");
			String pm = Matchers.match("window.redirect_uri=\"(\\S+?)\";", res);
			String redirect_uri = pm + "&fun=new";
			meta.setRedirect_uri(redirect_uri);

			String base_uri = redirect_uri.substring(0, redirect_uri.lastIndexOf("/"));
			meta.setBase_uri(base_uri);
		}
		if (code.equals("408")) {
			throw new WechatException("登录超时");
		}
		return code;
	}

	public void choiceSyncLine() throws WechatException {
		boolean enabled = false;
		for (String syncUrl : Constant.SYNC_HOST) {
			int[] res = this.syncCheck(syncUrl);
			if (res[0] == 0) {
				String url = "https://" + syncUrl + "/cgi-bin/mmwebwx-bin";
				meta.setWebpush_url(url);
				LOGGER.info("选择线路：[{}]", syncUrl);
				enabled = true;
				break;
			}
		}
		if (!enabled) {
			throw new WechatException("同步线路不通畅");
		}
	}

	public JSONObject webwxsync() throws WechatException {
		String url = meta.getBase_uri() + "/webwxsync?skey=" + meta.getSkey() + "&sid=" + meta.getWxsid();

		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("SyncKey", meta.getSyncKey());
		body.put("rr", DateKit.getCurrentUnixTime());

		// HttpRequest request =
		// HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie",
		// meta.getCookie())
		// .send(body.toString());

		// LOGGER.debug(request.toString());
		// String res = request.body();
		// request.disconnect();

		// if (StringKit.isBlank(res)) {
		// throw new WechatException("同步syncKey失败");
		// }
		// JSONObject jsonObject = JSONKit.parseObject(res);

		HttpRequsetUtil httpRequsetUtil = new HttpRequsetUtil(meta);
		JSONObject jsonObject = httpRequsetUtil.postJSON(url, body);
		JSONObject BaseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null != BaseResponse) {
			int ret = BaseResponse.getInt("Ret", -1);
			if (ret == 0) {
				meta.setSyncKey(jsonObject.get("SyncKey").asJSONObject());
				StringBuffer synckey = new StringBuffer();
				JSONArray list = meta.getSyncKey().get("List").asArray();
				for (int i = 0, len = list.size(); i < len; i++) {
					JSONObject item = list.get(i).asJSONObject();
					synckey.append("|" + item.getInt("Key", 0) + "_" + item.getInt("Val", 0));
				}
				meta.setSynckey(synckey.substring(1));
				return jsonObject;
			}
		}
		return null;
	}

	public static class QRCodeFrame extends JFrame {

		private static final long serialVersionUID = 8550014433017811556L;
		private JPanel contentPane;

		/**
		 * Create the frame.
		 */
		@SuppressWarnings("serial")
		public QRCodeFrame(final String filePath) {
			setBackground(Color.WHITE);
			this.setResizable(false);
			this.setTitle("\u8BF7\u7528\u624B\u673A\u626B\u63CF\u5FAE\u4FE1\u4E8C\u7EF4\u7801");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBounds(100, 100, 297, 362);
			this.contentPane = new JPanel();
			contentPane.setBackground(new Color(102, 153, 255));
			this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setContentPane(contentPane);
			contentPane.setLayout(null);

			JPanel qrcodePanel = new JPanel() {
				public void paintComponent(Graphics g) {
					ImageIcon icon = new ImageIcon(filePath);
					// 图片随窗体大小而变化
					g.drawImage(icon.getImage(), 0, 0, 301, 301, this);
				}
			};
			qrcodePanel.setBounds(0, 0, 295, 295);

			JLabel tipLable = new JLabel("扫描二维码登录微信");
			tipLable.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			tipLable.setHorizontalAlignment(SwingConstants.CENTER);
			tipLable.setBounds(0, 297, 291, 37);

			contentPane.add(qrcodePanel);
			contentPane.add(tipLable);

			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
	}
}
