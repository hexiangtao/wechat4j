package com.iyuexian.wechat4j.webweixin.dao;

public enum Table {
	accountConfig("webwx_accountConfig"),

	userAccount("webwx_userAccount"),

	message("webwx_message");

	private String name;

	Table(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
