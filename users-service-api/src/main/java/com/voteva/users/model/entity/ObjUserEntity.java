package com.voteva.users.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    private UUID userUid = UUID.randomUUID();
    private Timestamp userCreatedDtime = Timestamp.from(Instant.now());
    private boolean isBlocked = false;
    private boolean isAdmin;

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

    @Basic
    @Column(name = "blocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    public ObjUserEntity setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @Basic
    @Column(name = "admin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public ObjUserEntity setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
