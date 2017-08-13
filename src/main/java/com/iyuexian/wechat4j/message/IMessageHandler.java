package com.iyuexian.wechat4j.message;

import com.blade.kit.json.JSONObject;

public interface IMessageHandler {
	public void process(JSONObject msg);


}
