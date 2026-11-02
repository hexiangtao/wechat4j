package com.iyuexian.wechat4j.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 联系人实体
 * 
 * @author WeChat4j Team
 */
@Entity
@Table(name = "contact", indexes = {
    @Index(name = "idx_owner_user", columnList = "owner_user_id"),
    @Index(name = "idx_contact_user", columnList = "contact_user_id")
})
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_user_id", nullable = false, length = 100)
    private String ownerUserId;

    @Column(name = "contact_user_id", nullable = false, length = 100)
    private String contactUserId;

    @Column(name = "contact_username", length = 100)
    private String contactUsername;

    @Column(name = "contact_nickname", length = 100)
    private String contactNickname;

    @Column(name = "remark_name", length = 100)
    private String remarkName;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "signature", length = 500)
    private String signature;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "sex")
    private Integer sex; // 0-未知 1-男 2-女

    @Column(name = "contact_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(name = "is_friend")
    private Boolean isFriend = false;

    @Column(name = "is_blocked")
    private Boolean isBlocked = false;

    @Column(name = "is_top")
    private Boolean isTop = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum ContactType {
        FRIEND,         // 好友
        GROUP,          // 群组
        PUBLIC_ACCOUNT, // 公众号
        SPECIAL_ACCOUNT // 特殊账号
    }

    // 构造方法
    public Contact() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(String contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public String getContactNickname() {
        return contactNickname;
    }

    public void setContactNickname(String contactNickname) {
        this.contactNickname = contactNickname;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Boolean isFriend) {
        this.isFriend = isFriend;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}