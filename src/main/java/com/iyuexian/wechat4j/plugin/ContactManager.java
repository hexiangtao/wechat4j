package com.iyuexian.wechat4j.plugin;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONKit;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatException;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.http.WechatHttpClient;

public class ContactManager {

	public static Logger LOGGER = LoggerFactory.getLogger(ContactManager.class);
	private WechatMeta meta;

	private WechatHttpClient httpRequsetUtil;

	public ContactManager(WechatMeta meta) {
		super();
		this.meta = meta;
		this.httpRequsetUtil = new WechatHttpClient(meta);
	}

	/**
	 * 获取通讯录列表
	 */
	public JSONArray getContactList() {
		String url = meta.getBase_uri() + "/webwxgetcontact?&seq=0&pass_ticket=" + meta.getPass_ticket() + "&skey=" + meta.getSkey() + "&r="
				+ DateKit.getCurrentUnixTime();
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		JSONObject response = httpRequsetUtil.postJSON(url, body);
		JSONArray memberList = response.get("MemberList").asArray();
		return memberList;

	}

	public void initLatestChatroom(List<String> chatRommNames) {
		JSONArray groupArray = new JSONArray();
		// for (String groupUserName :
		// Storage.instance().getLatestChatRoomUserNameList()) {
		for (String groupUserName : chatRommNames) {
			JSONObject groupItem = new JSONObject();
			groupItem.put("UserName", groupUserName);
			groupItem.put("ChatRoomId", "");
			groupArray.add(groupItem);
		}
		JSONArray latestChatRoomList = getMemberListByChatroom(groupArray);
		Storage.instance().setLatestChatRoomList(latestChatRoomList);

	}

	public JSONArray getMemberListByChatroom(JSONArray chatRoomList) {
		String url = meta.getBase_uri() + "/webwxbatchgetcontact?type=ex&r=" + DateKit.getCurrentUnixTime() + "&lang=zh_CN";
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("Count", chatRoomList.size());
		body.put("List", chatRoomList);
		HttpRequest request = HttpRequest.post(url).contentType("application/json;charset=utf-8").header("Cookie", meta.getCookie())
				.send(body.toString());
		String res = request.body();
		request.disconnect();
		if (StringKit.isBlank(res)) {
			throw new WechatException("获取群成员列表失败");
		}
		JSONObject jsonObject = JSONKit.parseObject(res);
		JSONObject baseResponse = jsonObject.get("BaseResponse").asJSONObject();
		if (null == baseResponse || baseResponse.getInt("Ret", -1) != 0) {
			LOGGER.warn("获取群列表失败,{}", baseResponse);
			return null;
		}
		JSONArray memberList = jsonObject.get("ContactList").asArray();
		// LOGGER.info("获取群成员:{}", memberList);
		return memberList;

	}

}
