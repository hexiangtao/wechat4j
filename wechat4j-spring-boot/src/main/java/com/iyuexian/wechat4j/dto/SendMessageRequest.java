package com.iyuexian.wechat4j.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 发送消息请求DTO
 * 
 * @author WeChat4j Team
 */
@Schema(description = "发送消息请求")
public class SendMessageRequest {

    @Schema(description = "接收者用户ID", required = true)
    @NotBlank(message = "接收者用户ID不能为空")
    private String toUserId;

    @Schema(description = "消息内容", required = true)
    @NotBlank(message = "消息内容不能为空")
    private String content;

    @Schema(description = "消息类型", required = true, example = "TEXT")
    @NotNull(message = "消息类型不能为空")
    private MessageTypeDto messageType;

    @Schema(description = "是否为群消息", example = "false")
    private Boolean isGroupMessage = false;

    @Schema(description = "群组ID，群消息时必填")
    private String groupId;

    public enum MessageTypeDto {
        TEXT, IMAGE, VOICE, VIDEO, FILE
    }

    // 构造方法
    public SendMessageRequest() {}

    public SendMessageRequest(String toUserId, String content, MessageTypeDto messageType) {
        this.toUserId = toUserId;
        this.content = content;
        this.messageType = messageType;
    }

    // Getter和Setter方法
    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageTypeDto getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeDto messageType) {
        this.messageType = messageType;
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
}