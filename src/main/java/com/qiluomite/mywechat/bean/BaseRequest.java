package com.qiluomite.mywechat.bean;

public class BaseRequest {

	private String Uin; // xuin=1256242436
	private String Sid = ""; // cCrlbBx9gy5x/B/d
	private String Skey = ""; //
	private String DeviceID = ""; // e982371642000743

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
