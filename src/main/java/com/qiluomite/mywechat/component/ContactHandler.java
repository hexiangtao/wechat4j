package com.qiluomite.mywechat.component;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONKit;
import com.blade.kit.json.JSONObject;
import com.qiluomite.mywechat.bean.WechatMeta;
import com.qiluomite.mywechat.exception.WechatException;
import com.qiluomite.mywechat.util.HttpRequsetUtil;

public class ContactHandler {

	public static Logger LOGGER = LoggerFactory.getLogger(ContactHandler.class);
	private WechatMeta meta;

	private HttpRequsetUtil httpRequsetUtil;

	public ContactHandler(WechatMeta meta) {
		super();
		this.meta = meta;
		this.httpRequsetUtil = new HttpRequsetUtil(meta);
	}

	/**
	 * 获取通讯录列表
	 */
	public void initContactList() {
		String url = meta.getBase_uri() + "/webwxgetcontact?&seq=0&pass_ticket=" + meta.getPass_ticket() + "&skey=" + meta.getSkey() + "&r="
				+ DateKit.getCurrentUnixTime();
		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		JSONObject response = httpRequsetUtil.postJSON(url, body);
		JSONArray memberList = response.get("MemberList").asArray();
		Storage.instance().loadMemberList(memberList);

	}

	public void initLatestChatroom() {
		JSONArray groupArray = new JSONArray();
		for (String groupUserName : Storage.instance().getLatestChatRoomUserNameList()) {
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

	/**
	 * 
	 * @param userName
	 * @param status
	 *            2,default=2
	 * @param verifyContent
	 * @param autoUpdate
	 */
	public JSONObject addFriend(String userName, String verifyContent) {
		String url = "{0}/webwxverifyuser?r={1}&pass_ticket={2}";
		url = MessageFormat.format(url, meta.getBase_uri(), DateKit.getCurrentUnixTime(), meta.getPass_ticket());

		JSONObject body = initFriendRequsetParams(userName, verifyContent);
		JSONObject resp = httpRequsetUtil.postJSON(url, body);
		return resp;

	}

	public JSONObject initFriendRequsetParams(String userName, String verifyContent) {

		JSONObject body = new JSONObject();
		body.put("BaseRequest", meta.getBaseRequest());
		body.put("Opcode", 2);
		body.put("VerifyUserListSize", 1);
		JSONArray VerifyUserList = new JSONArray();
		JSONObject verifyItem = new JSONObject();
		verifyItem.put("Value", userName);
		verifyItem.put("VerifyUserTicket", "");
		VerifyUserList.add(verifyItem);
		body.put("VerifyUserList", VerifyUserList);
		body.put("VerifyContent", verifyContent);
		body.put("SceneListCount", 1);
		JSONArray sceneList = new JSONArray();
		sceneList.add(33);
		body.put("SceneList", sceneList);
		body.put("skey", meta.getSkey());
		return body;

	}

	

}
