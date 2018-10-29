package com.voteva.gateway.web.to.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.UUID;

public class UserInfo {

    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private UUID userUid;

    @Email
    @Size(max = 200)
    @ApiModelProperty(example = "user@example.com")
    private String email;

    @ApiModelProperty(example = "Vladimir")
    private String firstName;

    @ApiModelProperty(example = "Putin")
    private String lastName;

    @ApiModelProperty(example = "Putin")
    private long createdDatetime;

    @ApiModelProperty(example = "false")
    private boolean isBlocked;

    @ApiModelProperty(example = "false")
    private boolean isAdmin;

    @JsonGetter("user_uid")
    public UUID getUserUid() {
        return userUid;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    public UserInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonGetter("first_name")
    public String getFirstName() {
        return firstName;
    }

    public UserInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonGetter("last_name")
    public String getLastName() {
        return lastName;
    }

    public UserInfo setCreatedDatetime(long createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    @JsonGetter("created_datetime")
    public long getCreatedDatetime() {
        return createdDatetime;
    }

    public UserInfo setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @JsonGetter("is_blocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    public UserInfo setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    @JsonGetter("is_admin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public UserInfo setUserUid(UUID userUid) {
        this.userUid = userUid;
        return this;
    }
}
