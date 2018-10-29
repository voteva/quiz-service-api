package com.voteva.users.model.entity;

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
@Table(name = "users", schema = "users")
public class ObjUserEntity {

    private Integer userId;
    private UUID userUid = UUID.randomUUID();
    private String userEmail;
    private String userPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
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
    @Column(name = "user_email", length = 60, nullable = false)
    public String getUserEmail() {
        return userEmail;
    }

    public ObjUserEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public ObjUserEntity setUserPassword(String userPassword) {
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
