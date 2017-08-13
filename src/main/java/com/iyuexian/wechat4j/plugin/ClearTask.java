package com.iyuexian.wechat4j.plugin;

import com.iyuexian.wechat4j.core.Task;

/*
 * 定时清除聊天缓存变量
 */

public class ClearTask extends Task {
	int pauseTime;

	public ClearTask(int p) {
		pauseTime = p;
	}

	@Override
	public void initTask() {
		while (true) {
			RecordContainer.cache.clear();
			System.out.println("清除缓存成功！");
			try {
				Thread.sleep(pauseTime * 60000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}
}
