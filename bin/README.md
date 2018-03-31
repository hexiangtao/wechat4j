# wechat4j
用java实现的微信客户端，支持自动聊天, 消息监听，自动回复，添加好友，获取群成员列表,自动记录聊天记录，自动下载图片，语音，视频消息

# 启动方式
###  1:控制台启动
    自定任意类，在main方法下用WecahtStartup.login()实现扫码登陆
```
WechatMeta meta = WechatStartup.login();	
```
登陆成功后，会返回WechatMeta实例，里面包含了当前扫码用户的登陆信息，后面所有的功能都需要该实例
WechatApiUtils类封装了web微信的相关接口，具体功能实现在plugin及其实子包里，您也可以在这基础上扩展


## 2:以web方式启动，需要在config.properties文件里配置mysql的帐号密码



QQ交流群:580889921


