package com.voteva.users.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "users")
public class ObjUserEntity {

    private int userId;
    private String userEmail;
    private String fullName;
    private UUID userUid = UUID.randomUUID();
    private Timestamp userCreatedDtime = Timestamp.from(Instant.now());

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public int getUserId() {
        return userId;
    }

    public ObjUserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "user_email", nullable = false)
    public String getUserEmail() {
        return userEmail;
    }

    public ObjUserEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public ObjUserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Basic
    @Column(name = "user_uid", nullable = false)
    public UUID getUserUid() {
        return userUid;
    }

    public ObjUserEntity setUserUid(UUID userUid) {
        this.userUid = userUid;
        return this;
    }

    @Basic
    @Column(name = "created_date", nullable = false)
    public Timestamp getUserCreatedDtime() {
        return userCreatedDtime;
    }

    public ObjUserEntity setUserCreatedDtime(Timestamp userCreatedDtime) {
        this.userCreatedDtime = userCreatedDtime;
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
