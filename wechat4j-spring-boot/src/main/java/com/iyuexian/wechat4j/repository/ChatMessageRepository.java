package com.iyuexian.wechat4j.repository;

import com.iyuexian.wechat4j.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 聊天消息数据访问层
 * 
 * @author WeChat4j Team
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 根据消息ID查找消息
     */
    Optional<ChatMessage> findByMessageId(String messageId);

    /**
     * 查找两个用户之间的聊天记录
     */
    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.fromUserId = :userId1 AND m.toUserId = :userId2) OR " +
           "(m.fromUserId = :userId2 AND m.toUserId = :userId1) " +
           "AND m.isDeleted = false " +
           "ORDER BY m.createdAt ASC")
    Page<ChatMessage> findChatHistory(@Param("userId1") String userId1,
                                     @Param("userId2") String userId2,
                                     Pageable pageable);

    /**
     * 查找用户的所有聊天记录
     */
    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.fromUserId = :userId OR m.toUserId = :userId) " +
           "AND m.isDeleted = false " +
           "ORDER BY m.createdAt DESC")
    Page<ChatMessage> findUserMessages(@Param("userId") String userId, Pageable pageable);

    /**
     * 查找群聊消息
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.groupId = :groupId " +
           "AND m.isGroupMessage = true AND m.isDeleted = false " +
           "ORDER BY m.createdAt ASC")
    Page<ChatMessage> findGroupMessages(@Param("groupId") String groupId, Pageable pageable);

    /**
     * 查找未读消息
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.toUserId = :userId " +
           "AND m.isRead = false AND m.isDeleted = false " +
           "ORDER BY m.createdAt ASC")
    List<ChatMessage> findUnreadMessages(@Param("userId") String userId);

    /**
     * 统计未读消息数量
     */
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.toUserId = :userId " +
           "AND m.isRead = false AND m.isDeleted = false")
    long countUnreadMessages(@Param("userId") String userId);

    /**
     * 统计两个用户间的消息数量
     */
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE " +
           "((m.fromUserId = :userId1 AND m.toUserId = :userId2) OR " +
           "(m.fromUserId = :userId2 AND m.toUserId = :userId1)) " +
           "AND m.isDeleted = false")
    long countMessagesBetweenUsers(@Param("userId1") String userId1, 
                                  @Param("userId2") String userId2);

    /**
     * 根据消息类型查找消息
     */
    List<ChatMessage> findByMessageTypeAndIsDeletedFalse(ChatMessage.MessageType messageType, Pageable pageable);

    /**
     * 查找指定时间范围内的消息
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.createdAt BETWEEN :startTime AND :endTime " +
           "AND m.isDeleted = false ORDER BY m.createdAt ASC")
    List<ChatMessage> findMessagesByTimeRange(@Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);

    /**
     * 标记消息为已读
     */
    @Modifying
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.id IN :messageIds")
    int markMessagesAsRead(@Param("messageIds") List<Long> messageIds);

    /**
     * 批量标记用户所有未读消息为已读
     */
    @Modifying
    @Query("UPDATE ChatMessage m SET m.isRead = true WHERE m.toUserId = :userId AND m.isRead = false")
    int markAllUserMessagesAsRead(@Param("userId") String userId);

    /**
     * 软删除消息
     */
    @Modifying
    @Query("UPDATE ChatMessage m SET m.isDeleted = true WHERE m.id = :messageId")
    int deleteMessage(@Param("messageId") Long messageId);

    /**
     * 批量软删除消息
     */
    @Modifying
    @Query("UPDATE ChatMessage m SET m.isDeleted = true WHERE m.id IN :messageIds")
    int deleteMessages(@Param("messageIds") List<Long> messageIds);

    /**
     * 删除过期消息（物理删除）
     */
    @Modifying
    @Query("DELETE FROM ChatMessage m WHERE m.createdAt < :expireTime")
    int deleteExpiredMessages(@Param("expireTime") LocalDateTime expireTime);

    /**
     * 搜索消息内容
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.content LIKE %:keyword% " +
           "AND m.isDeleted = false ORDER BY m.createdAt DESC")
    Page<ChatMessage> searchMessagesByContent(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 检查消息是否存在
     */
    boolean existsByMessageId(String messageId);
}