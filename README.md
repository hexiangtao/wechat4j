### wechat4j 说明

<p>用java实现的微信客户端助手，支持自动聊天, 消息监听，自动回复，添加好友，获取群成员列表,自动保存聊天记录您的电脑上，自动下载图片，语音，视频消息</p>

<p>其它功能正进一步完善中</p>

<p>QQ群:580889921</p>

##  程序入口

````
package com.iyuexian.wechat4j;

import com.iyuexian.wechat4j.core.WechatCore;

public class App extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.run();

	}

}

````


您可以使用自定义类继承WechatCore,添加自己需要的组件，例如下面代码添加了一个MessageListener，监听微信消息
````
package com.iyuexian.wechat4j;
import com.iyuexian.wechat4j.core.WechatCore;
import com.iyuexian.wechat4j.plugin.MessageListener;

public class  MyClass extends WechatCore {

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		MessageListener t1 = new MessageListener(app);
		app.run(t1);

	}

}
````

## 默认未开启机器人功能，您可以在Config类打开如下设置
````
	/*****是否开启自动回复******/
	public static final boolean AUTO_REPLY = false;
	/*****是否打印成员信息****/
	public static final boolean PRINT_MEMBER_INFO = true;
	/******是否开启自动添加好友******/
	public static final boolean ADD_FRIEND = false;

`````

## 配置文件说明:

````
###机器人接入密钥######
chat.key=7507257e2872417d9e4fb0e2764cf0ac
chat.secret=36c3be794b4950dc
chat.server_url=http://www.tuling123.com/openapi/api

######消息本地存储路径####
app.media_path=C:/wechat
app.msg_location=C:/wechat/msg.txt

````


程序包结构,如下 </br>
![Alt text](https://github.com/enohe/wechat4j/blob/master/%E5%8C%85%E7%BB%93%E6%9E%84.png)

### 包结构说明:
#####  com.iyuexian.wechat4j    入口类
##### com.iyuexian.wechat4j.config  配置信息及系统常量 
##### com.iyuexian.wechat4j.core   核心功能实现
##### com.iyuexian.wechat4j.message   各种消息处理
##### com.iyuexian.wechat4j.model     定义的各种实体类，比如，好友,微信群...




####### WechatCore类启动步骤

####### 在run方法中依次调用如下方法
````
geetUUID()       获取微信接口获取UUID
showQrcode()	 显示二维码,待用户扫码登陆		
beginUserTask()   开始用户自定义的任务
`````



以下是使用截图
######### 控制台启动
![Alt text](https://github.com/enohe/wechat4j/blob/master/console.png)

########## 聊天
![Alt text](https://github.com/enohe/wechat4j/blob/master/chat.png)






