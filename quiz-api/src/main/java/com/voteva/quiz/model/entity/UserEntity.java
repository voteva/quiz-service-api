package com.voteva.quiz.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
@Table(name = "users", schema = "quiz")
public class UserEntity {

    private Integer id;
    private UUID userUid;
    private String firstName;
    private String lastName;
    private boolean isAdmin = false;
    private boolean isBlocked = false;
    private Timestamp userCreatedDtime = Timestamp.from(Instant.now());

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public UserEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "user_uid", nullable = false)
    public UUID getUserUid() {
        return userUid;
    }

    public UserEntity setUserUid(UUID userUid) {
        this.userUid = userUid;
        return this;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Basic
    @Column(name = "admin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public UserEntity setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    @Basic
    @Column(name = "blocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    public UserEntity setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @Basic
    @Column(name = "created_date", nullable = false)
    public Timestamp getUserCreatedDtime() {
        return userCreatedDtime;
    }

    public UserEntity setUserCreatedDtime(Timestamp userCreatedDtime) {
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
