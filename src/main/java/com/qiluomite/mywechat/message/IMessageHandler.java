package com.qiluomite.mywechat.message;

import com.blade.kit.json.JSONObject;

public interface IMessageHandler {
	public void process(JSONObject msg);


}
