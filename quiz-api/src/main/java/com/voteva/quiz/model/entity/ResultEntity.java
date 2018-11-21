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
@Table(name = "results", schema = "quiz")
public class ResultEntity {

    private ResultId resultId;
    private int percent;
    private int attemptsAllowed = 100;

    @EmbeddedId
    public ResultId getResultId() {
        return resultId;
    }

    public ResultEntity setResultId(ResultId resultId) {
        this.resultId = resultId;
        return this;
    }

    @Basic
    @Min(0)
    @Max(100)
    @Column(name = "percent")
    public int getPercent() {
        return percent;
    }

    public ResultEntity setPercent(int percent) {
        this.percent = percent;
        return this;
    }

    @Basic
    @Min(0)
    @Column(name = "attempts_allowed")
    public int getAttemptsAllowed() {
        return attemptsAllowed;
    }

    public ResultEntity setAttemptsAllowed(int attemptsAllowed) {
        this.attemptsAllowed = attemptsAllowed;
        return this;
    }

    @Embeddable
    public static class ResultId implements Serializable {
        private UUID userUid;
        private UUID testUid;

        public ResultId() {}

        public ResultId(UUID userUid, UUID testUid) {
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
