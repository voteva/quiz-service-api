package com.voteva.tests.model.entity;

import com.voteva.tests.model.ref.QuestionRef;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection = "tests")
public class TestEntity {

    @Id
    private ObjectId _id;

    @Indexed(unique = true)
    @Field(value = "testUid")
    private UUID testUid = UUID.randomUUID();

    @Indexed(unique = true)
    @Field(value = "testName")
    private String testName;

    @Field(value = "testCategory")
    private String testCategory;

    @Field(value = "questions")
    private List<QuestionRef> questions;

    public UUID getTestUid() {
        return testUid;
    }

    public TestEntity setTestUid(UUID testUid) {
        this.testUid = testUid;
        return this;
    }

    public String getTestName() {
        return testName;
    }

    public TestEntity setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    public String getTestCategory() {
        return testCategory;
    }

    public TestEntity setTestCategory(String testCategory) {
        this.testCategory = testCategory;
        return this;
    }

    public List<QuestionRef> getQuestions() {
        return questions;
    }

    public TestEntity setQuestions(List<QuestionRef> questions) {
        this.questions = questions;
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
