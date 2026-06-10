package com.iyuexian.wechat4j.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 用于异步消息处理
 * 
 * @author WeChat4j Team
 */
@Configuration
public class RabbitMQConfig {

    // 交换机名称
    public static final String WECHAT_EXCHANGE = "wechat.exchange";
    
    // 队列名称
    public static final String MESSAGE_QUEUE = "wechat.message.queue";
    public static final String FILE_QUEUE = "wechat.file.queue";
    public static final String NOTIFICATION_QUEUE = "wechat.notification.queue";
    
    // 路由键
    public static final String MESSAGE_ROUTING_KEY = "wechat.message";
    public static final String FILE_ROUTING_KEY = "wechat.file";
    public static final String NOTIFICATION_ROUTING_KEY = "wechat.notification";

    /**
     * 声明交换机
     */
    @Bean
    public TopicExchange wechatExchange() {
        return new TopicExchange(WECHAT_EXCHANGE, true, false);
    }

    /**
     * 声明消息队列
     */
    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(MESSAGE_QUEUE)
                .withArgument("x-dead-letter-exchange", WECHAT_EXCHANGE + ".dlx")
                .withArgument("x-dead-letter-routing-key", "dead.message")
                .build();
    }

    /**
     * 声明文件队列
     */
    @Bean
    public Queue fileQueue() {
        return QueueBuilder.durable(FILE_QUEUE)
                .withArgument("x-dead-letter-exchange", WECHAT_EXCHANGE + ".dlx")
                .withArgument("x-dead-letter-routing-key", "dead.file")
                .build();
    }

    /**
     * 声明通知队列
     */
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", WECHAT_EXCHANGE + ".dlx")
                .withArgument("x-dead-letter-routing-key", "dead.notification")
                .build();
    }

    /**
     * 绑定消息队列
     */
    @Bean
    public Binding messageBinding(Queue messageQueue, TopicExchange wechatExchange) {
        return BindingBuilder.bind(messageQueue)
                .to(wechatExchange)
                .with(MESSAGE_ROUTING_KEY);
    }

    /**
     * 绑定文件队列
     */
    @Bean
    public Binding fileBinding(Queue fileQueue, TopicExchange wechatExchange) {
        return BindingBuilder.bind(fileQueue)
                .to(wechatExchange)
                .with(FILE_ROUTING_KEY);
    }

    /**
     * 绑定通知队列
     */
    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange wechatExchange) {
        return BindingBuilder.bind(notificationQueue)
                .to(wechatExchange)
                .with(NOTIFICATION_ROUTING_KEY);
    }

    /**
     * 死信交换机
     */
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(WECHAT_EXCHANGE + ".dlx", true, false);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(WECHAT_EXCHANGE + ".dead.queue").build();
    }

    /**
     * 绑定死信队列
     */
    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("dead.*");
    }

    /**
     * 配置RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setExchange(WECHAT_EXCHANGE);
        
        // 配置确认机制
        template.setMandatory(true);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // 消息发送成功
            } else {
                // 消息发送失败
                System.err.println("消息发送失败: " + cause);
            }
        });
        
        template.setReturnsCallback(returned -> {
            // 消息无法路由到队列时的回调
            System.err.println("消息无法路由: " + returned.getMessage());
        });
        
        return template;
    }

    /**
     * 配置监听器容器工厂
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        
        // 配置并发消费者
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        
        // 配置预取数量
        factory.setPrefetchCount(10);
        
        // 配置确认模式
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        
        return factory;
    }
}