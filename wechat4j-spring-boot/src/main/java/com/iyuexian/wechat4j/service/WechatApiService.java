package com.iyuexian.wechat4j.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iyuexian.wechat4j.entity.WechatUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 微信API服务接口
 * 重构原WechatApiUtil为Spring管理的服务
 * 
 * @author WeChat4j Team
 */
public interface WechatApiService {

    /**
     * 获取UUID
     */
    String getUUID() throws Exception;

    /**
     * 生成二维码
     */
    String generateQrCode(String uuid) throws Exception;

    /**
     * 等待登录扫码
     */
    Map<String, Object> waitForLogin(String uuid, int timeout) throws Exception;

    /**
     * 执行登录
     */
    WechatUser login(String uuid, String loginInfo) throws Exception;

    /**
     * 初始化微信
     */
    JSONObject initWeChat(String userId) throws Exception;

    /**
     * 状态通知
     */
    boolean sendStatusNotify(String userId) throws Exception;

    /**
     * 发送文本消息
     */
    boolean sendTextMessage(String fromUserId, String toUserId, String content) throws Exception;

    /**
     * 发送图片消息
     */
    boolean sendImageMessage(String fromUserId, String toUserId, MultipartFile image) throws Exception;

    /**
     * 发送文件消息
     */
    boolean sendFileMessage(String fromUserId, String toUserId, MultipartFile file) throws Exception;

    /**
     * 获取联系人列表
     */
    @Cacheable(value = "contacts", key = "#userId")
    List<JSONObject> getContactList(String userId) throws Exception;

    /**
     * 获取群成员列表
     */
    @Cacheable(value = "groupMembers", key = "#groupId")
    List<JSONObject> getGroupMembers(String userId, String groupId) throws Exception;

    /**
     * 同步消息
     */
    JSONArray syncMessage(String userId, String syncKey) throws Exception;

    /**
     * 获取消息
     */
    List<JSONObject> getMessages(String userId) throws Exception;

    /**
     * 下载文件
     */
    byte[] downloadFile(String userId, String fileId) throws Exception;

    /**
     * 添加好友
     */
    boolean addFriend(String userId, String targetUserId, String message) throws Exception;

    /**
     * 退出登录
     */
    boolean logout(String userId) throws Exception;

    /**
     * 检查登录状态
     */
    boolean checkLoginStatus(String userId) throws Exception;

    /**
     * 获取用户头像
     */
    byte[] getUserAvatar(String userId, String targetUserId) throws Exception;

    /**
     * 上传媒体文件
     */
    String uploadMedia(String userId, MultipartFile file) throws Exception;

    /**
     * 创建群聊
     */
    String createChatRoom(String userId, List<String> memberIds, String roomName) throws Exception;

    /**
     * 修改群聊名称
     */
    boolean updateChatRoomName(String userId, String roomId, String newName) throws Exception;

    /**
     * 邀请好友进群
     */
    boolean inviteFriendToChatRoom(String userId, String roomId, List<String> friendIds) throws Exception;

    /**
     * 移除群成员
     */
    boolean removeMemberFromChatRoom(String userId, String roomId, List<String> memberIds) throws Exception;

    /**
     * 发送位置消息
     */
    boolean sendLocationMessage(String fromUserId, String toUserId, double latitude, double longitude, String address) throws Exception;

    /**
     * 发送链接消息
     */
    boolean sendLinkMessage(String fromUserId, String toUserId, String url, String title, String description) throws Exception;
}