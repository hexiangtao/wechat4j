package com.qiluomite.mywechat.tuling123;

public class UserInfo {

	private String apiKey;
	private String userId;

	
	
	
	public UserInfo(String apiKey, String userId) {
		this.apiKey = apiKey;
		this.userId = userId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
