package com.voteva.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Configuration
@ConfigurationProperties(prefix = "gateway.web")
public class WebProperties {

    @NotNull
    private CorsProperties cors;

    public CorsProperties getCors() {
        return cors;
    }

    public WebProperties setCors(CorsProperties cors) {
        this.cors = cors;
        return this;
    }

    public static class CorsProperties {
        private List<String> allowedOrigins;

        public List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public CorsProperties setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
            return this;
        }
    }
}
