package com.iyuexian.wechat4j.service;

import com.iyuexian.wechat4j.dto.SendMessageRequest;
import com.iyuexian.wechat4j.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息服务接口
 * 
 * @author WeChat4j Team
 */
public interface MessageService {

    /**
     * 发送消息
     */
    boolean sendMessage(SendMessageRequest request) throws Exception;

    /**
     * 保存消息记录
     */
    ChatMessage saveMessage(ChatMessage message);

    /**
     * 获取聊天历史
     */
    Page<ChatMessage> getChatHistory(String userId1, String userId2, Pageable pageable);

    /**
     * 获取用户所有消息
     */
    Page<ChatMessage> getUserMessages(String userId, Pageable pageable);

    /**
     * 获取群聊消息
     */
    Page<ChatMessage> getGroupMessages(String groupId, Pageable pageable);

    /**
     * 获取未读消息
     */
    List<ChatMessage> getUnreadMessages(String userId);

    /**
     * 统计未读消息数量
     */
    long countUnreadMessages(String userId);

    /**
     * 标记消息为已读
     */
    boolean markMessagesAsRead(List<Long> messageIds);

    /**
     * 标记用户所有消息为已读
     */
    boolean markAllUserMessagesAsRead(String userId);

    /**
     * 删除消息
     */
    boolean deleteMessage(Long messageId);

    /**
     * 批量删除消息
     */
    boolean deleteMessages(List<Long> messageIds);

    /**
     * 搜索消息
     */
    Page<ChatMessage> searchMessages(String keyword, Pageable pageable);

    /**
     * 获取指定时间范围内的消息
     */
    List<ChatMessage> getMessagesByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计两个用户间的消息数量
     */
    long countMessagesBetweenUsers(String userId1, String userId2);

    /**
     * 清理过期消息
     */
    int cleanExpiredMessages(LocalDateTime expireTime);

    /**
     * 处理接收到的消息
     */
    void processReceivedMessage(ChatMessage message);

    /**
     * 转发消息
     */
    boolean forwardMessage(String fromUserId, String toUserId, Long originalMessageId) throws Exception;
}