# wechatRobot
一个用微信web接口实现的微信聊天机器人，支持消息监听，自动回复，添加好友，获取群成员列表


##  程序启动入口


```import com.qiluomite.mywechat.component.WechatCore;

public class App extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.run();

	}

}```


入口类说明
   定义任意类继承WechatCore，在main方法里调用该由该类的run()方法即可




