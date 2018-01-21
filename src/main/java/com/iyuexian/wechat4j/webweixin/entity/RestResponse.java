package com.iyuexian.wechat4j.webweixin.entity;

public class RestResponse {

	private int code = FAIL;
	private String message;
	private Object data;

	public static final int OK = 0;
	public static final int FAIL = -1000;

	public boolean isOk() {
		return code == OK;
	}

	public RestResponse() {
	}

	public RestResponse(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static RestResponse success(String message, Object data) {
		return new RestResponse(OK, message, data);

	}

	public static RestResponse success(Object data) {
		return success("OK", data);
	}

	public static RestResponse fail(String message, Object data) {
		return new RestResponse(FAIL, message, data);

	}

	public static RestResponse fail(String msg) {
		return fail(msg,null);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
