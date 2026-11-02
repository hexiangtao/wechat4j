package com.iyuexian.wechat4j.service.impl;

import com.iyuexian.wechat4j.dto.SendMessageRequest;
import com.iyuexian.wechat4j.entity.ChatMessage;
import com.iyuexian.wechat4j.entity.ChatMessage.MessageType;
import com.iyuexian.wechat4j.repository.ChatMessageRepository;
import com.iyuexian.wechat4j.service.MessageService;
import com.iyuexian.wechat4j.service.WechatApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 消息服务实现类
 * 
 * @author WeChat4j Team
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private WechatApiService wechatApiService;

    @Override
    public boolean sendMessage(SendMessageRequest request) throws Exception {
        // For now, we'll use a default fromUserId - in a real application this would come from authentication
        String fromUserId = "CURRENT_USER"; // This should be obtained from SecurityContext
        
        logger.info("发送消息: {} -> {}", fromUserId, request.getToUserId());
        
        try {
            // Map DTO enum to entity enum
            MessageType entityMessageType = mapDtoToEntityMessageType(request.getMessageType());
            
            // 调用微信API发送消息
            boolean success = sendMessageByType(fromUserId, request.getToUserId(), 
                request.getContent(), entityMessageType);
            
            if (success) {
                // 保存消息记录
                ChatMessage message = new ChatMessage();
                message.setMessageId(generateMessageId());
                message.setFromUserId(fromUserId);
                message.setToUserId(request.getToUserId());
                message.setContent(request.getContent());
                message.setMessageType(entityMessageType);
                message.setCreatedAt(LocalDateTime.now());
                message.setIsGroupMessage(request.getIsGroupMessage());
                message.setIsRead(false);
                message.setIsDeleted(false);
                
                if (request.getIsGroupMessage()) {
                    message.setGroupId(request.getToUserId());
                }
                
                saveMessage(message);
                logger.info("消息发送成功并已保存: {}", message.getMessageId());
            }
            
            return success;
        } catch (Exception e) {
            logger.error("发送消息失败", e);
            throw e;
        }
    }

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        if (message.getCreatedAt() == null) {
            message.setCreatedAt(LocalDateTime.now());
        }
        if (message.getMessageId() == null || message.getMessageId().isEmpty()) {
            message.setMessageId(generateMessageId());
        }
        
        return chatMessageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessage> getChatHistory(String userId1, String userId2, Pageable pageable) {
        return chatMessageRepository.findChatHistory(userId1, userId2, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessage> getUserMessages(String userId, Pageable pageable) {
        return chatMessageRepository.findUserMessages(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessage> getGroupMessages(String groupId, Pageable pageable) {
        return chatMessageRepository.findGroupMessages(groupId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getUnreadMessages(String userId) {
        return chatMessageRepository.findUnreadMessages(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadMessages(String userId) {
        return chatMessageRepository.countUnreadMessages(userId);
    }

    @Override
    public boolean markMessagesAsRead(List<Long> messageIds) {
        try {
            int updated = chatMessageRepository.markMessagesAsRead(messageIds);
            logger.info("标记了 {} 条消息为已读", updated);
            return updated > 0;
        } catch (Exception e) {
            logger.error("标记消息为已读失败", e);
            return false;
        }
    }

    @Override
    public boolean markAllUserMessagesAsRead(String userId) {
        try {
            int updated = chatMessageRepository.markAllUserMessagesAsRead(userId);
            logger.info("标记用户 {} 的 {} 条消息为已读", userId, updated);
            return updated > 0;
        } catch (Exception e) {
            logger.error("标记用户消息为已读失败", e);
            return false;
        }
    }

    @Override
    public boolean deleteMessage(Long messageId) {
        try {
            int updated = chatMessageRepository.deleteMessage(messageId);
            logger.info("逻辑删除消息: {}", messageId);
            return updated > 0;
        } catch (Exception e) {
            logger.error("删除消息失败", e);
            return false;
        }
    }

    @Override
    public boolean deleteMessages(List<Long> messageIds) {
        try {
            int updated = chatMessageRepository.deleteMessages(messageIds);
            logger.info("批量删除了 {} 条消息", updated);
            return updated > 0;
        } catch (Exception e) {
            logger.error("批量删除消息失败", e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessage> searchMessages(String keyword, Pageable pageable) {
        return chatMessageRepository.searchMessagesByContent(keyword, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getMessagesByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return chatMessageRepository.findMessagesByTimeRange(startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public long countMessagesBetweenUsers(String userId1, String userId2) {
        return chatMessageRepository.countMessagesBetweenUsers(userId1, userId2);
    }

    @Override
    public int cleanExpiredMessages(LocalDateTime expireTime) {
        try {
            int deleted = chatMessageRepository.deleteExpiredMessages(expireTime);
            logger.info("清理了 {} 条过期消息", deleted);
            return deleted;
        } catch (Exception e) {
            logger.error("清理过期消息失败", e);
            return 0;
        }
    }

    @Override
    public void processReceivedMessage(ChatMessage message) {
        logger.debug("处理接收到的消息: {}", message.getMessageId());
        
        // 保存消息
        saveMessage(message);
        
        // 可以在这里添加其他处理逻辑，比如：
        // - 推送通知
        // - 自动回复
        // - 关键词检测
        // - 消息转发等
        
        logger.info("成功处理消息: {} from {} to {}", 
            message.getMessageId(), message.getFromUserId(), message.getToUserId());
    }

    @Override
    public boolean forwardMessage(String fromUserId, String toUserId, Long originalMessageId) throws Exception {
        logger.info("转发消息: {} -> {}, 原消息ID: {}", fromUserId, toUserId, originalMessageId);
        
        try {
            Optional<ChatMessage> originalMessage = chatMessageRepository.findById(originalMessageId);
            if (!originalMessage.isPresent()) {
                logger.error("原消息不存在: {}", originalMessageId);
                return false;
            }
            
            ChatMessage original = originalMessage.get();
            
            // 创建转发请求
            SendMessageRequest forwardRequest = new SendMessageRequest();
            forwardRequest.setToUserId(toUserId);
            forwardRequest.setContent(original.getContent());
            forwardRequest.setMessageType(mapEntityToDtoMessageType(original.getMessageType()));
            forwardRequest.setIsGroupMessage(false);
            
            return sendMessage(forwardRequest);
        } catch (Exception e) {
            logger.error("转发消息失败", e);
            throw e;
        }
    }

    /**
     * 生成消息ID
     */
    private String generateMessageId() {
        return "MSG_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    /**
     * Send message by type using the appropriate WechatApiService method
     */
    private boolean sendMessageByType(String fromUserId, String toUserId, String content, MessageType messageType) throws Exception {
        switch (messageType) {
            case TEXT:
                return wechatApiService.sendTextMessage(fromUserId, toUserId, content);
            case IMAGE:
            case VOICE:
            case VIDEO:
            case FILE:
                // For file-based messages, we need different handling
                // For now, we'll just send as text message
                logger.warn("文件类型消息暂不支持，转为文本消息发送: {}", messageType);
                return wechatApiService.sendTextMessage(fromUserId, toUserId, "[" + messageType + "] " + content);
            default:
                return wechatApiService.sendTextMessage(fromUserId, toUserId, content);
        }
    }

    /**
     * Map DTO MessageTypeDto to Entity MessageType
     */
    private MessageType mapDtoToEntityMessageType(SendMessageRequest.MessageTypeDto dtoType) {
        switch (dtoType) {
            case TEXT:
                return MessageType.TEXT;
            case IMAGE:
                return MessageType.IMAGE;
            case VOICE:
                return MessageType.VOICE;
            case VIDEO:
                return MessageType.VIDEO;
            case FILE:
                return MessageType.FILE;
            default:
                return MessageType.TEXT;
        }
    }

    /**
     * Map Entity MessageType to DTO MessageTypeDto
     */
    private SendMessageRequest.MessageTypeDto mapEntityToDtoMessageType(MessageType entityType) {
        switch (entityType) {
            case TEXT:
                return SendMessageRequest.MessageTypeDto.TEXT;
            case IMAGE:
                return SendMessageRequest.MessageTypeDto.IMAGE;
            case VOICE:
                return SendMessageRequest.MessageTypeDto.VOICE;
            case VIDEO:
                return SendMessageRequest.MessageTypeDto.VIDEO;
            case FILE:
                return SendMessageRequest.MessageTypeDto.FILE;
            default:
                return SendMessageRequest.MessageTypeDto.TEXT;
        }
    }
}