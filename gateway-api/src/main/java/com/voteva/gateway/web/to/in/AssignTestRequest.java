package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AssignTestRequest {

    @NotNull
    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private UUID userUid;

    @NotNull
    @ApiModelProperty(example = "8152e3f9-449f-44c1-9d3a-e3c6f1afd752")
    private UUID testUid;

    @Min(1)
    @ApiModelProperty(example = "1")
    private int attemptsAllowed;

    @JsonCreator
    public AssignTestRequest(
            @JsonProperty("userUid") UUID userUid,
            @JsonProperty("testUid") UUID testUid,
            @JsonProperty("attemptsAllowed") int attemptsAllowed) {
        this.userUid = userUid;
        this.testUid = testUid;
        this.attemptsAllowed = attemptsAllowed;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public UUID getTestUid() {
        return testUid;
    }

    public int getAttemptsAllowed() {
        return attemptsAllowed;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
