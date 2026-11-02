package com.iyuexian.wechat4j.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置
 * 用于实时消息推送
 * 
 * @author WeChat4j Team
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用内存消息代理，用于向客户端发送消息
        config.enableSimpleBroker("/topic", "/queue");
        
        // 设置应用程序目标前缀
        config.setApplicationDestinationPrefixes("/app");
        
        // 设置用户目标前缀（用于点对点消息）
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册STOMP端点
        registry.addEndpoint("/websocket")
                .setAllowedOriginPatterns("*")  // 允许所有来源（生产环境应限制）
                .withSockJS();  // 启用SockJS回退选项
    }
}