package wcaht4j;


import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.iyuexian.wechat4j.WechatMeta;
import com.iyuexian.wechat4j.WechatStartup;
import com.iyuexian.wechat4j.plugin.ContactManager;
import com.iyuexian.wechat4j.plugin.message.TxtMessageHandler;

public class ClientTest {

	public static void main(String[] args) throws InterruptedException {

		WechatMeta meta = WechatStartup.login();

		ContactManager handler = new ContactManager(meta);

		TxtMessageHandler messageHandler = new TxtMessageHandler(meta);
		JSONArray contactList = handler.getContactList();
		for (int i = 0; i < contactList.size(); i++) {
			JSONObject val = contactList.get(i).asJSONObject();
			String userName = val.getString("UserName");
			String nickName = val.getString("NickName");
			String remarkName = val.getString("RemarkName");
			System.out.println(val.toString());
			if (remarkName.equals("Test")) {
				System.out.println("userName:" + userName + ",nickName:" + nickName);
				messageHandler.webwxsendmsg("你好。。。。http://www.baidu.com", userName);
			}
		}

	}

}