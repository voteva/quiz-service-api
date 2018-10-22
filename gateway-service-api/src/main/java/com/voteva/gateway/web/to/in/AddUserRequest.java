package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AddUserRequest {

    @Email
    @Size(max = 200)
    @ApiModelProperty(example = "user@example.com")
    private final String email;

    @NotBlank
    @Size(max = 200)
    @ApiModelProperty(example = "Jeff Bezos")
    private final String fullName;

    @JsonCreator
    public AddUserRequest(
            @JsonProperty("email") String email,
            @JsonProperty("full_name") String fullName) {
        this.email = email;
        this.fullName = fullName;
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
