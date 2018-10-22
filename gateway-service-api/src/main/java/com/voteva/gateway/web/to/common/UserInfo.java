package com.voteva.gateway.web.to.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.UUID;

public class UserInfo {

    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private final UUID userUid;

    @Email
    @Size(max = 200)
    @ApiModelProperty(example = "user@example.com")
    private final String email;

    private final String fullName;

    private final long createdDatetime;

    private final boolean isBlocked;

    private final boolean isAdmin;

    @JsonCreator
    public UserInfo(@JsonProperty("user_uid") UUID userUid,
                    @JsonProperty("email") String email,
                    @JsonProperty("full_name") String fullName,
                    @JsonProperty("created_datetime") long createdDatetime,
                    @JsonProperty("is_blocked") boolean isBlocked,
                    @JsonProperty("is_admin") boolean isAdmin) {
        this.userUid = userUid;
        this.email = email;
        this.fullName = fullName;
        this.createdDatetime = createdDatetime;
        this.isBlocked = isBlocked;
        this.isAdmin = isAdmin;
    }

    @JsonGetter("user_uid")
    public UUID getUserUid() {
        return userUid;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonGetter("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonGetter("created_datetime")
    public long getCreatedDatetime() {
        return createdDatetime;
    }

    @JsonGetter("is_blocked")
    public boolean isBlocked() {
        return isBlocked;
    }

    @JsonGetter("is_admin")
    public boolean isAdmin() {
        return isAdmin;
    }
}
