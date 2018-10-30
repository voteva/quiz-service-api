package com.voteva.quiz.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "rel_user_2_test", schema = "quiz")
public class RelUser2TestEntity {

    private User2TestId user2TestId;
    private int percent = 0;
    private int attemptsAllowed = 100;

    @EmbeddedId
    public User2TestId getUser2TestId() {
        return user2TestId;
    }

    public RelUser2TestEntity setUser2TestId(User2TestId user2TestId) {
        this.user2TestId = user2TestId;
        return this;
    }

    @Basic
    @Min(0)
    @Max(100)
    @Column(name = "percent")
    public int getPercent() {
        return percent;
    }

    public RelUser2TestEntity setPercent(int percent) {
        this.percent = percent;
        return this;
    }

    @Basic
    @Min(0)
    @Column(name = "attempts_allowed")
    public int getAttemptsAllowed() {
        return attemptsAllowed;
    }

    public RelUser2TestEntity setAttemptsAllowed(int attemptsAllowed) {
        this.attemptsAllowed = attemptsAllowed;
        return this;
    }

    @Embeddable
    public static class User2TestId implements Serializable {
        private UUID userUid;
        private UUID testUid;

        public User2TestId() {}

        public User2TestId(UUID userUid, UUID testUid) {
            this.userUid = userUid;
            this.testUid = testUid;
        }

        @Column(name = "user_uid", nullable = false)
        public UUID getUserUid() {
            return userUid;
        }

        public void setUserUid(UUID userUid) {
            this.userUid = userUid;
        }

        @Column(name = "test_uid", nullable = false)
        public UUID getTestUid() {
            return testUid;
        }

        public void setTestUid(UUID testUid) {
            this.testUid = testUid;
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o, false);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
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
