package com.voteva.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotEmpty;

public class LoginUserRequest {

    //@SafeHtml
    @NotEmpty
    private String username;

    //@SafeHtml
    @NotEmpty
    private String password;

    @JsonCreator
    public LoginUserRequest(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("password", "[PROTECTED]")
                .toString();
    }
}
