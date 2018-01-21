package com.iyuexian.wechat4j.webweixin.entity;

import java.util.Date;

public class AccountConfig {

	private long id;
	private String mobile;
	private String name;
	private String val;
	private Date refreshDate;

	public static final String NAME_AUTO_REPLAY = "auto_reply";
	public static final String ON = "ON";
	public static final String OFF = "OFF";

	public boolean isON() {
		return this.val.equalsIgnoreCase(ON);
	}

	public boolean isOFF() {
		return this.val.equalsIgnoreCase(OFF);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

}
