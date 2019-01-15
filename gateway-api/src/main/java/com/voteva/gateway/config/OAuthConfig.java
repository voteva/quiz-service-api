package com.voteva.gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties(prefix = "gateway.oauth")
public class OAuthConfig {

    @NotNull
    private String redirectOauthUriTemplate;

    public String getRedirectOauthUriTemplate() {
        return redirectOauthUriTemplate;
    }

    public void setRedirectOauthUriTemplate(String redirectOauthUriTemplate) {
        this.redirectOauthUriTemplate = redirectOauthUriTemplate;
    }
}
