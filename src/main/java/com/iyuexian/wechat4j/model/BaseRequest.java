package com.iyuexian.wechat4j.model;

public class BaseRequest {

	private String Uin;
	private String Sid = "";
	private String Skey = "";
	private String DeviceID = "";

	public String getUin() {
		return Uin;
	}

	public String getSid() {
		return Sid;
	}

	public String getSkey() {
		return Skey;
	}

	public String getDeviceID() {
		return DeviceID;
	}

	public void setUin(String uin) {
		Uin = uin;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public void setSkey(String skey) {
		Skey = skey;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

}
