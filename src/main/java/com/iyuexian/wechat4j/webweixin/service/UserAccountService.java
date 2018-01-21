package com.iyuexian.wechat4j.webweixin.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.plugin.MessageMonitorTask;
import com.iyuexian.wechat4j.util.Matchers;
import com.iyuexian.wechat4j.webweixin.WechatApiUtils;
import com.iyuexian.wechat4j.webweixin.dao.AccountConfigDao;
import com.iyuexian.wechat4j.webweixin.dao.UserAccountDao;
import com.iyuexian.wechat4j.webweixin.entity.RestResponse;
import com.iyuexian.wechat4j.webweixin.entity.UserAccount;
import com.iyuexian.wechat4j.webweixin.util.StrUtils;

public class UserAccountService {

	private UserAccountDao userAccountDao = new UserAccountDao();

	private AccountConfigDao accountConfigDao = new AccountConfigDao();

	static final Logger logger = LoggerFactory.getLogger(UserAccountService.class);

	public void reloadAccount() {
		List<UserAccount> accountList = userAccountDao.list(1, 100);
		for (UserAccount account : accountList) {
			String loginData = account.getLoginData();
			if (StrUtils.isBlank(loginData)) {
				continue;
			}
			WechatMeta meta = JSON.parseObject(loginData, WechatMeta.class);
			if (meta == null || meta.getLoginDate() == null) {
				continue;
			}
			long maxDate = meta.getLoginDate().getTime() + 1000 * 60 * 60 * 24;
			if (maxDate < System.currentTimeMillis()) {
				continue;
			}
			logger.warn("begin initAccount for:{}", meta.getMobile());
			initAccount(meta);
		}
	}

	public void initWechat(String res, String mobile) {

		String pm = Matchers.match("window.redirect_uri=\"(\\S+?)\";", res);
		String redirectUrl = pm + "&fun=new";
		String baseUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("/"));
		WechatMeta meta = new WechatMeta();
		meta.setMobile(mobile);
		meta.setRedirect_uri(redirectUrl);
		meta.setBase_uri(baseUrl);
		meta.setLoginDate(new Date());
		userAccountDao.updateLoginData(mobile, JSON.toJSONString(meta));
		userAccountDao.commint();
		initAccount(meta);

	}

	private void initAccount(WechatMeta meta) {
		WechatApiUtils.login(meta);
		WechatApiUtils.wxInit(meta);
		WechatApiUtils.openStatusNotify(meta);
		// WechatApiUtils.getContactList(meta); // 获取联系人列表
		MessageMonitorTask messageMonitorTask = new MessageMonitorTask(meta);
		messageMonitorTask.start();
	}

	public RestResponse login(String mobile, String pwd) {

		UserAccount account = userAccountDao.selectOne(mobile, pwd);
		if (account == null) {
			return RestResponse.fail("手机号或密码错误");
		}
		return RestResponse.success(account);
	}

	public RestResponse reg(String mobile, String pwd) {

		UserAccount account = userAccountDao.selectOne(mobile, null);
		if (account != null) {
			return RestResponse.fail("该手机号已经被使用");
		}
		account = new UserAccount();
		account.setMobile(mobile);
		account.setPwd(pwd);
		userAccountDao.insert(account);
		userAccountDao.commint();
		return RestResponse.success(account);
	}

	public RestResponse updateConfig(String mobile, String name, String value) {

		accountConfigDao.update(mobile, name, value);
		accountConfigDao.commint();
		return RestResponse.success("OK");
	}

}
