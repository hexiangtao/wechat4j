package com.iyuexian.wechat4j.webweixin.entity;

import java.util.Date;

public class UserAccount {

	private long id;
	private String mobile = "";
	private String pwd = "";
	private String email = "";
	private String emailpwd = "";
	private int status = STATUS_OR_NORMAL;
	private int type = TYPE_OF_TEMP;
	private Date createDate = new Date();
	private String loginData;

	public static final int TYPE_OF_TEMP = 0;
	public static final int TYPE_OF_VIP = 1;
	public static final int TYPE_OF_FOREVER = 2;
	public static final int STATUS_OR_NORMAL = 0;
	public static final int STATUS_OF_STOP = 1;

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailpwd() {
		return emailpwd;
	}

	public void setEmailpwd(String emailpwd) {
		this.emailpwd = emailpwd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLoginData() {
		return loginData;
	}

	public void setLoginData(String loginData) {
		this.loginData = loginData;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", mobile=" + mobile + ", pwd=" + pwd + ", email=" + email + ", emailpwd=" + emailpwd + ", status=" + status
				+ ", type=" + type + ", createDate=" + createDate + "]";
	}

}
