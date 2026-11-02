package com.iyuexian.wechat4j.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 聊天消息实体
 * 
 * @author WeChat4j Team
 */
@Entity
@Table(name = "chat_message", indexes = {
    @Index(name = "idx_from_user", columnList = "from_user_id"),
    @Index(name = "idx_to_user", columnList = "to_user_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", unique = true, nullable = false, length = 100)
    private String messageId;

    @Column(name = "from_user_id", nullable = false, length = 100)
    private String fromUserId;

    @Column(name = "from_user_name", length = 100)
    private String fromUserName;

    @Column(name = "to_user_id", nullable = false, length = 100)
    private String toUserId;

    @Column(name = "to_user_name", length = 100)
    private String toUserName;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "message_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "is_group_message")
    private Boolean isGroupMessage = false;

    @Column(name = "group_id", length = 100)
    private String groupId;

    @Column(name = "group_name", length = 100)
    private String groupName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public enum MessageType {
        TEXT,           // 文本消息
        IMAGE,          // 图片消息
        VOICE,          // 语音消息
        VIDEO,          // 视频消息
        FILE,           // 文件消息
        LOCATION,       // 位置消息
        LINK,           // 链接消息
        SYSTEM,         // 系统消息
        CARD,           // 名片消息
        EMOJI,          // 表情消息
        RED_PACKET,     // 红包消息
        TRANSFER        // 转账消息
    }

    // 构造方法
    public ChatMessage() {
        this.createdAt = LocalDateTime.now();
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Boolean getIsGroupMessage() {
        return isGroupMessage;
    }

    public void setIsGroupMessage(Boolean isGroupMessage) {
        this.isGroupMessage = isGroupMessage;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}