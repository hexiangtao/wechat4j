package com.qiluomite.mywechat.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.config.Config;
import com.qiluomite.mywechat.exception.WechatException;
import com.qiluomite.mywechat.message.FileHandler;

public abstract class WechatCore extends WechatMeta {

	protected Logger logger = LoggerFactory.getLogger(WechatCore.class);

	private LoginHandler loginHandler;
	private ContactHandler contactHandler;
	private MessageHandler messageHandler;
	private FileHandler fileHandler;
	private List<Task> userTasks = new ArrayList<Task>();

	public WechatCore() {
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
		fileHandler.uploadFile("C:/gym_buglist.docx", null);

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
