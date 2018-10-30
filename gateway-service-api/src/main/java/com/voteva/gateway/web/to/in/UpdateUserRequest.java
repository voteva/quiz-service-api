package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class UpdateUserRequest {

    @NotNull
    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private final UUID userUid;

    @Email
    @Size(max = 200)
    @ApiModelProperty(example = "user@example.com")
    private final String email;

    @Size(max = 200)
    @ApiModelProperty(example = "Jeff Bezos")
    private final String fullName;

    @JsonCreator
    public UpdateUserRequest(
            @JsonProperty("userUid") UUID userUid,
            @JsonProperty("email") String email,
            @JsonProperty("fullName") String fullName) {
        this.userUid = userUid;
        this.email = email;
        this.fullName = fullName;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
