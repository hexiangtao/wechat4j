# WeChat4j Spring Boot 版本

这是对原 WeChat4j 项目的 Spring Boot 重构版本，采用现代化的分层架构和最佳实践。

## 项目特性

### 🏗️ 架构改进

- **分层架构**: 采用标准的 Controller → Service → Repository 分层架构
- **依赖注入**: 全面使用 Spring 的依赖注入管理组件
- **配置管理**: 使用 application.yml 统一管理配置
- **异常处理**: 统一的异常处理和错误响应格式

### 🚀 新增功能

- **RESTful API**: 提供完整的 HTTP API 接口
- **实时通信**: 基于 WebSocket 的实时消息推送
- **消息队列**: 集成 RabbitMQ 进行异步消息处理
- **缓存支持**: 使用 Redis 缓存用户信息和聊天记录
- **安全认证**: Spring Security 保护 API 接口
- **API 文档**: 集成 Swagger/OpenAPI 3 自动生成文档

### 📊 数据持久化

- **JPA/Hibernate**: 使用 Spring Data JPA 进行数据持久化
- **实体设计**: 设计了用户、消息、联系人等核心实体
- **数据库支持**: 支持 MySQL/H2 数据库

### 🎯 开发体验

- **热重载**: 支持开发时的热重载
- **配置处理**: 智能的配置属性处理
- **监控端点**: 集成 Actuator 监控应用状态
- **日志管理**: 完善的日志配置和管理

## 技术栈

- **Spring Boot 2.7.14** - 应用框架
- **Spring Web** - Web 层框架
- **Spring Data JPA** - 数据持久化
- **Spring Security** - 安全框架
- **Spring Cache** - 缓存抽象
- **Spring WebSocket** - WebSocket 支持
- **RabbitMQ** - 消息队列
- **Redis** - 缓存数据库
- **MySQL** - 关系数据库
- **Swagger/OpenAPI 3** - API 文档
- **Jackson** - JSON 处理

## 快速开始

### 环境要求

- Java 8+
- Maven 3.6+
- MySQL 5.7+ (可选，默认使用 H2)
- Redis 3.0+ (可选)
- RabbitMQ 3.8+ (可选)

### 安装运行

1. **克隆项目**
```bash
git clone <repository-url>
cd wechat4j-spring-boot
```

2. **配置数据库** (可选)
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wechat4j
    username: your_username
    password: your_password
```

3. **启动应用**
```bash
mvn spring-boot:run
```

4. **访问应用**
- 应用地址: http://localhost:8080/api
- Web 界面: http://localhost:8080
- API 文档: http://localhost:8080/swagger-ui.html
- 健康检查: http://localhost:8080/actuator/health

### 默认登录信息

- 管理员: `admin / admin123`
- 普通用户: `user / user123`

## API 使用示例

### 1. 获取登录二维码

```bash
curl -X GET http://localhost:8080/api/wechat/login/uuid
```

### 2. 发送消息

```bash
curl -X POST http://localhost:8080/api/wechat/message/send \
  -H "Content-Type: application/json" \
  -H "X-User-Id: your_user_id" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "toUserId": "target_user_id",
    "content": "Hello, World!",
    "messageType": "TEXT"
  }'
```

### 3. 获取联系人列表

```bash
curl -X GET http://localhost:8080/api/wechat/contacts \
  -H "X-User-Id: your_user_id" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

## 配置说明

### 应用配置

```yaml
# 服务器配置
server:
  port: 8080
  servlet:
    context-path: /api

# 微信相关配置
wechat:
  login-timeout: 5          # 登录超时时间（分钟）
  message-poll-interval: 1000  # 消息轮询间隔（毫秒）
  download-path: ./downloads/   # 文件下载目录
  qrcode-path: ./qrcodes/      # 二维码保存目录
  auto-reply-enabled: true     # 自动回复开关
```

### 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wechat4j
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:password}
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
```

### Redis 配置

```yaml
spring:
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
    database: 0
```

### RabbitMQ 配置

```yaml
spring:
  rabbitmq:
    host: ${RABBITMQ_HOST:localhost}
    port: ${RABBITMQ_PORT:5672}
    username: ${RABBITMQ_USERNAME:guest}
    password: ${RABBITMQ_PASSWORD:guest}
```

## 项目结构

```
src/main/java/com/iyuexian/wechat4j/
├── Wechat4jApplication.java     # 应用入口
├── config/                      # 配置类
│   ├── SecurityConfig.java      # 安全配置
│   ├── WebSocketConfig.java     # WebSocket配置
│   └── RabbitMQConfig.java      # 消息队列配置
├── controller/                  # 控制器层
│   └── WechatController.java    # 微信API控制器
├── service/                     # 服务层
│   ├── WechatApiService.java    # 微信API服务接口
│   ├── MessageService.java      # 消息服务接口
│   └── impl/                    # 服务实现
├── repository/                  # 数据访问层
│   ├── WechatUserRepository.java
│   └── ChatMessageRepository.java
├── entity/                      # 实体类
│   ├── WechatUser.java         # 用户实体
│   ├── ChatMessage.java        # 消息实体
│   └── Contact.java            # 联系人实体
├── dto/                         # 数据传输对象
│   ├── SendMessageRequest.java  # 发送消息请求
│   └── ApiResponse.java         # API响应格式
├── client/                      # HTTP客户端
│   └── WechatHttpClient.java    # 微信HTTP客户端
└── utils/                       # 工具类
    └── QrCodeUtil.java          # 二维码工具
```

## 开发指南

### 添加新的消息类型

1. 在 `ChatMessage.MessageType` 枚举中添加新类型
2. 在 `SendMessageRequest.MessageTypeDto` 中添加对应类型
3. 在 `WechatApiService` 中实现对应的发送方法
4. 在 `WechatController` 中添加对应的API接口

### 添加新的API接口

1. 在 `WechatController` 中添加新的方法
2. 使用 `@Operation` 注解添加API文档
3. 添加必要的参数验证和错误处理
4. 在 `WechatApiService` 中实现业务逻辑

### 自定义消息处理

1. 实现 `MessageHandler` 接口
2. 使用 `@Component` 注解注册为Spring组件
3. 在 `MessageService` 中注入并使用

## 部署说明

### Docker 部署

1. **构建镜像**
```bash
mvn clean package
docker build -t wechat4j-spring-boot .
```

2. **运行容器**
```bash
docker run -d \
  --name wechat4j \
  -p 8080:8080 \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=password \
  wechat4j-spring-boot
```

### 生产环境配置

```yaml
# application-prod.yml
spring:
  profiles: prod
  
  datasource:
    hikari:
      maximum-pool-size: 50
      
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    
logging:
  level:
    com.iyuexian.wechat4j: INFO
    root: WARN
```

## 监控和维护

### 健康检查

```bash
curl http://localhost:8080/api/actuator/health
```

### 应用指标

```bash
curl http://localhost:8080/api/actuator/metrics
```

### 日志级别调整

```bash
curl -X POST http://localhost:8080/api/actuator/loggers/com.iyuexian.wechat4j \
  -H "Content-Type: application/json" \
  -d '{"configuredLevel": "DEBUG"}'
```

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 更新日志

### v2.0.0
- 完全重构为Spring Boot架构
- 新增RESTful API接口
- 集成WebSocket实时通信
- 添加消息队列支持
- 实现缓存机制
- 添加安全认证
- 集成API文档

### v1.x
- 原版WeChat4j功能
- Swing桌面客户端
- 基础微信协议支持

## 支持

如有问题或建议，请提交 [Issue](https://github.com/hexiangtao/wechat4j/issues)。

## 致谢

感谢原 WeChat4j 项目的贡献者们，本项目在其基础上进行了现代化改造。