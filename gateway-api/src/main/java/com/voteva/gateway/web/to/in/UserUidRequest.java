package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UserUidRequest {

    @NotNull
    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private final UUID userUid;

    @JsonCreator
    public UserUidRequest(@JsonProperty("userUid") UUID userUid) {
        this.userUid = userUid;
    }

    public UUID getUserUid() {
        return userUid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
