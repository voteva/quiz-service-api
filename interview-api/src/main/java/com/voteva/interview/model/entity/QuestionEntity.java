package com.voteva.interview.model.entity;

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
@Table(name = "questions", schema = "interview")
public class QuestionEntity {

    private Integer id;
    private UUID questionUid = UUID.randomUUID();
    private String questionName;
    private String questionText;
    private String questionAnswer;
    private String category;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public QuestionEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "question_uid", nullable = false)
    public UUID getQuestionUid() {
        return questionUid;
    }

    public QuestionEntity setQuestionUid(UUID questionUid) {
        this.questionUid = questionUid;
        return this;
    }

    @Basic
    @Column(name = "question_name", nullable = false, length = 60)
    public String getQuestionName() {
        return questionName;
    }

    public QuestionEntity setQuestionName(String questionName) {
        this.questionName = questionName;
        return this;
    }

    @Basic
    @Column(name = "question_text", nullable = false)
    public String getQuestionText() {
        return questionText;
    }

    public QuestionEntity setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    @Basic
    @Column(name = "question_answer", nullable = false)
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public QuestionEntity setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
        return this;
    }

    @Basic
    @Column(name = "category", nullable = false, length = 60)
    public String getCategory() {
        return category;
    }

    public QuestionEntity setCategory(String category) {
        this.category = category;
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
