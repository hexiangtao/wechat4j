package com.qiluomite.mywechat.component;

public abstract class Task {
	public void start() {
		try {
			new Thread(wrapWithRunnable(), "user-tasks-" + System.currentTimeMillis()).start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Runnable wrapWithRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				initTask();
			}
		};
	}
	public abstract void initTask();

}
