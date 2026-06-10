package com.iyuexian.wechat4j.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iyuexian.wechat4j.dto.ApiResponse;
import com.iyuexian.wechat4j.dto.SendMessageRequest;
import com.iyuexian.wechat4j.entity.WechatUser;
import com.iyuexian.wechat4j.service.MessageService;
import com.iyuexian.wechat4j.service.WechatApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信API控制器
 * 
 * @author WeChat4j Team
 */
@RestController
@RequestMapping("/wechat")
@Tag(name = "微信API", description = "微信客户端API接口")
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class WechatController {

    private static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatApiService wechatApiService;

    @Autowired
    private MessageService messageService;

    // 登录会话缓存
    private final Map<String, String> loginSessions = new ConcurrentHashMap<>();

    @Operation(summary = "获取登录UUID", description = "获取微信登录二维码UUID")
    @GetMapping("/login/uuid")
    public ApiResponse<String> getLoginUUID() {
        try {
            String uuid = wechatApiService.getUUID();
            return ApiResponse.success("获取UUID成功", uuid);
        } catch (Exception e) {
            logger.error("获取UUID失败", e);
            return ApiResponse.error("获取UUID失败: " + e.getMessage());
        }
    }

    @Operation(summary = "生成登录二维码", description = "生成微信登录二维码图片")
    @PostMapping("/login/qrcode")
    public ApiResponse<String> generateQrCode(
            @Parameter(description = "登录UUID", required = true) 
            @RequestParam @NotBlank String uuid) {
        try {
            String qrCodePath = wechatApiService.generateQrCode(uuid);
            return ApiResponse.success("生成二维码成功", qrCodePath);
        } catch (Exception e) {
            logger.error("生成二维码失败", e);
            return ApiResponse.error("生成二维码失败: " + e.getMessage());
        }
    }

    @Operation(summary = "等待扫码登录", description = "等待用户扫码确认登录")
    @GetMapping("/login/wait")
    public ApiResponse<Map<String, Object>> waitForLogin(
            @Parameter(description = "登录UUID", required = true)
            @RequestParam @NotBlank String uuid,
            @Parameter(description = "超时时间（秒）", example = "30")
            @RequestParam(defaultValue = "30") int timeout) {
        try {
            Map<String, Object> result = wechatApiService.waitForLogin(uuid, timeout);
            return ApiResponse.success("查询登录状态成功", result);
        } catch (Exception e) {
            logger.error("等待登录失败", e);
            return ApiResponse.error("等待登录失败: " + e.getMessage());
        }
    }

    @Operation(summary = "确认登录", description = "确认扫码登录并初始化用户会话")
    @PostMapping("/login/confirm")
    public ApiResponse<WechatUser> confirmLogin(
            @Parameter(description = "登录UUID", required = true)
            @RequestParam @NotBlank String uuid,
            @Parameter(description = "登录信息")
            @RequestParam String loginInfo) {
        try {
            WechatUser user = wechatApiService.login(uuid, loginInfo);
            
            // 初始化微信
            JSONObject initResult = wechatApiService.initWeChat(user.getUserId());
            
            // 发送状态通知
            wechatApiService.sendStatusNotify(user.getUserId());
            
            // 缓存登录会话
            loginSessions.put(uuid, user.getUserId());
            
            logger.info("用户登录成功: {}", user.getNickname());
            return ApiResponse.success("登录成功", user);
        } catch (Exception e) {
            logger.error("确认登录失败", e);
            return ApiResponse.error("确认登录失败: " + e.getMessage());
        }
    }

    @Operation(summary = "发送文本消息", description = "发送文本消息给指定用户")
    @PostMapping("/message/send")
    public ApiResponse<Boolean> sendMessage(
            @Parameter(description = "发送者用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Valid @RequestBody SendMessageRequest request) {
        try {
            boolean success = false;
            
            switch (request.getMessageType()) {
                case TEXT:
                    success = wechatApiService.sendTextMessage(userId, request.getToUserId(), request.getContent());
                    break;
                default:
                    return ApiResponse.badRequest("不支持的消息类型: " + request.getMessageType());
            }
            
            if (success) {
                return ApiResponse.success("消息发送成功", true);
            } else {
                return ApiResponse.error("消息发送失败");
            }
        } catch (Exception e) {
            logger.error("发送消息失败", e);
            return ApiResponse.error("发送消息失败: " + e.getMessage());
        }
    }

    @Operation(summary = "发送图片消息", description = "发送图片消息给指定用户")
    @PostMapping(value = "/message/send/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Boolean> sendImageMessage(
            @Parameter(description = "发送者用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "接收者用户ID", required = true)
            @RequestParam @NotBlank String toUserId,
            @Parameter(description = "图片文件", required = true)
            @RequestParam("image") MultipartFile image) {
        try {
            boolean success = wechatApiService.sendImageMessage(userId, toUserId, image);
            
            if (success) {
                return ApiResponse.success("图片发送成功", true);
            } else {
                return ApiResponse.error("图片发送失败");
            }
        } catch (Exception e) {
            logger.error("发送图片失败", e);
            return ApiResponse.error("发送图片失败: " + e.getMessage());
        }
    }

    @Operation(summary = "发送文件消息", description = "发送文件消息给指定用户")
    @PostMapping(value = "/message/send/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Boolean> sendFileMessage(
            @Parameter(description = "发送者用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "接收者用户ID", required = true)
            @RequestParam @NotBlank String toUserId,
            @Parameter(description = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            boolean success = wechatApiService.sendFileMessage(userId, toUserId, file);
            
            if (success) {
                return ApiResponse.success("文件发送成功", true);
            } else {
                return ApiResponse.error("文件发送失败");
            }
        } catch (Exception e) {
            logger.error("发送文件失败", e);
            return ApiResponse.error("发送文件失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取联系人列表", description = "获取当前用户的联系人列表")
    @GetMapping("/contacts")
    public ApiResponse<List<JSONObject>> getContacts(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId) {
        try {
            List<JSONObject> contacts = wechatApiService.getContactList(userId);
            return ApiResponse.success("获取联系人列表成功", contacts);
        } catch (Exception e) {
            logger.error("获取联系人列表失败", e);
            return ApiResponse.error("获取联系人列表失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取群成员列表", description = "获取指定群组的成员列表")
    @GetMapping("/groups/{groupId}/members")
    public ApiResponse<List<JSONObject>> getGroupMembers(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "群组ID", required = true)
            @PathVariable @NotBlank String groupId) {
        try {
            List<JSONObject> members = wechatApiService.getGroupMembers(userId, groupId);
            return ApiResponse.success("获取群成员列表成功", members);
        } catch (Exception e) {
            logger.error("获取群成员列表失败", e);
            return ApiResponse.error("获取群成员列表失败: " + e.getMessage());
        }
    }

    @Operation(summary = "同步消息", description = "同步最新的消息")
    @GetMapping("/messages/sync")
    public ApiResponse<JSONArray> syncMessages(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "同步键")
            @RequestParam(required = false) String syncKey) {
        try {
            JSONArray messages = wechatApiService.syncMessage(userId, syncKey);
            return ApiResponse.success("同步消息成功", messages);
        } catch (Exception e) {
            logger.error("同步消息失败", e);
            return ApiResponse.error("同步消息失败: " + e.getMessage());
        }
    }

    @Operation(summary = "添加好友", description = "向指定用户发送好友请求")
    @PostMapping("/friends/add")
    public ApiResponse<Boolean> addFriend(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "目标用户ID", required = true)
            @RequestParam @NotBlank String targetUserId,
            @Parameter(description = "验证消息")
            @RequestParam(required = false, defaultValue = "我是%s") String message) {
        try {
            boolean success = wechatApiService.addFriend(userId, targetUserId, message);
            
            if (success) {
                return ApiResponse.success("好友请求发送成功", true);
            } else {
                return ApiResponse.error("好友请求发送失败");
            }
        } catch (Exception e) {
            logger.error("添加好友失败", e);
            return ApiResponse.error("添加好友失败: " + e.getMessage());
        }
    }

    @Operation(summary = "创建群聊", description = "创建新的群聊")
    @PostMapping("/groups/create")
    public ApiResponse<String> createGroup(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "群成员ID列表", required = true)
            @RequestParam List<String> memberIds,
            @Parameter(description = "群名称")
            @RequestParam(required = false) String roomName) {
        try {
            String groupId = wechatApiService.createChatRoom(userId, memberIds, roomName);
            return ApiResponse.success("创建群聊成功", groupId);
        } catch (Exception e) {
            logger.error("创建群聊失败", e);
            return ApiResponse.error("创建群聊失败: " + e.getMessage());
        }
    }

    @Operation(summary = "邀请好友进群", description = "邀请好友加入群聊")
    @PostMapping("/groups/{groupId}/invite")
    public ApiResponse<Boolean> inviteToGroup(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId,
            @Parameter(description = "群组ID", required = true)
            @PathVariable @NotBlank String groupId,
            @Parameter(description = "好友ID列表", required = true)
            @RequestParam List<String> friendIds) {
        try {
            boolean success = wechatApiService.inviteFriendToChatRoom(userId, groupId, friendIds);
            
            if (success) {
                return ApiResponse.success("邀请好友成功", true);
            } else {
                return ApiResponse.error("邀请好友失败");
            }
        } catch (Exception e) {
            logger.error("邀请好友进群失败", e);
            return ApiResponse.error("邀请好友进群失败: " + e.getMessage());
        }
    }

    @Operation(summary = "退出登录", description = "退出当前登录状态")
    @PostMapping("/logout")
    public ApiResponse<Boolean> logout(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId) {
        try {
            boolean success = wechatApiService.logout(userId);
            
            // 清理会话缓存
            loginSessions.values().removeIf(cachedUserId -> cachedUserId.equals(userId));
            
            if (success) {
                return ApiResponse.success("退出登录成功", true);
            } else {
                return ApiResponse.error("退出登录失败");
            }
        } catch (Exception e) {
            logger.error("退出登录失败", e);
            return ApiResponse.error("退出登录失败: " + e.getMessage());
        }
    }

    @Operation(summary = "检查登录状态", description = "检查用户当前登录状态")
    @GetMapping("/status")
    public ApiResponse<Boolean> checkStatus(
            @Parameter(description = "用户ID", required = true)
            @RequestHeader("X-User-Id") @NotBlank String userId) {
        try {
            boolean isOnline = wechatApiService.checkLoginStatus(userId);
            return ApiResponse.success("状态检查成功", isOnline);
        } catch (Exception e) {
            logger.error("检查登录状态失败", e);
            return ApiResponse.error("检查登录状态失败: " + e.getMessage());
        }
    }
}