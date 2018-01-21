package com.iyuexian.wechat4j.webweixin.entity;

import java.util.Date;

import com.iyuexian.wechat4j.config.Enums.MsgType;

public class Message {

	private long id;
	private String mobile = "";
	private String content = "";
	private int type = MsgType.TXT.getType();
	private String remark = "";
	private Date createDate = new Date();

	public Message() {
	}

	public Message(String mobile, String content, int type, String remark) {
		this.mobile = mobile;
		this.content = content;
		this.type = type;
		this.remark = remark;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
