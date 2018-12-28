package com.voteva.auth.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "obj_users", schema = "users")
public class UserEntity {

    private Integer userId;
    private UUID userUid = UUID.randomUUID();
    private String userEmail;
    private String userPassword;

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getUserId() {
        return userId;
    }

    public UserEntity setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "user_uid", nullable = false)
    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    @Basic
    @Column(name = "email", length = 60, nullable = false)
    public String getUserEmail() {
        return userEmail;
    }

    public UserEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    @Basic
    @Column(name = "encrypted_password")
    public String getUserPassword() {
        return userPassword;
    }

    public UserEntity setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
