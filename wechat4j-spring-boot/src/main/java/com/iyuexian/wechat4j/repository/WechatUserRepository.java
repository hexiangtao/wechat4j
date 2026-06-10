package com.iyuexian.wechat4j.repository;

import com.iyuexian.wechat4j.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 微信用户数据访问层
 * 
 * @author WeChat4j Team
 */
@Repository
public interface WechatUserRepository extends JpaRepository<WechatUser, Long> {

    /**
     * 根据用户ID查找用户
     */
    Optional<WechatUser> findByUserId(String userId);

    /**
     * 根据用户名查找用户
     */
    Optional<WechatUser> findByUsername(String username);

    /**
     * 根据会话ID查找用户
     */
    Optional<WechatUser> findBySessionId(String sessionId);

    /**
     * 查找指定状态的用户
     */
    List<WechatUser> findByStatus(WechatUser.UserStatus status);

    /**
     * 查找在线用户
     */
    List<WechatUser> findByStatusIn(List<WechatUser.UserStatus> statuses);

    /**
     * 更新用户状态
     */
    @Modifying
    @Query("UPDATE WechatUser u SET u.status = :status, u.updatedAt = :updatedAt WHERE u.userId = :userId")
    int updateUserStatus(@Param("userId") String userId, 
                        @Param("status") WechatUser.UserStatus status, 
                        @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 更新用户最后登录时间
     */
    @Modifying
    @Query("UPDATE WechatUser u SET u.lastLoginAt = :lastLoginAt, u.updatedAt = :updatedAt WHERE u.userId = :userId")
    int updateLastLoginTime(@Param("userId") String userId, 
                           @Param("lastLoginAt") LocalDateTime lastLoginAt,
                           @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * 根据用户ID删除用户
     */
    void deleteByUserId(String userId);

    /**
     * 统计在线用户数量
     */
    @Query("SELECT COUNT(u) FROM WechatUser u WHERE u.status = 'ONLINE'")
    long countOnlineUsers();

    /**
     * 查找长时间未活动的用户
     */
    @Query("SELECT u FROM WechatUser u WHERE u.lastLoginAt < :beforeTime")
    List<WechatUser> findInactiveUsers(@Param("beforeTime") LocalDateTime beforeTime);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查用户ID是否存在
     */
    boolean existsByUserId(String userId);
}