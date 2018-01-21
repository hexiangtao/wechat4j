package com.iyuexian.wechat4j.webweixin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.util.PropertyReader;
import com.iyuexian.wechat4j.webweixin.service.UserAccountService;

@WebListener
public class WebContextListener implements ServletContextListener {
	static final Logger logger = LoggerFactory.getLogger(WebContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("contextDestroy");

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.debug("contextInitialized");
		System.setProperty("https.protocols", "TLSv1");
		System.setProperty("jsse.enableSNIExtension", "false");
		PropertyReader configReader = PropertyReader.load("config.properties");
		if (configReader != null) {
			Constant.configReader = configReader;
			logger.debug("load config.properties=>" + Constant.configReader.asMap());
		}
		try {
			UserAccountService userAccountService = new UserAccountService();
			userAccountService.reloadAccount();
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}

	}

}
