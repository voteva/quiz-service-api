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
    @Size(max = 100)
    @ApiModelProperty(example = "user@example.com")
    private final String email;

    @NotBlank
    @Size(max = 40)
    @ApiModelProperty(example = "Ivan")
    private final String firstName;

    @NotBlank
    @Size(max = 40)
    @ApiModelProperty(example = "Ivanov")
    private final String lastName;

    @ApiModelProperty(example = "false")
    private final boolean isAdmin;

    @JsonCreator
    public AddUserRequest(
            @JsonProperty("email") String email,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("isAdmin") boolean isAdmin) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("isAdmin", isAdmin)
                .toString();
    }
}
