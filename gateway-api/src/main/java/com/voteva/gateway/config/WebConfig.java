package com.voteva.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Validated
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final WebProperties webProperties;

    @Autowired
    public WebConfig(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> corsAllowedOrigins = webProperties.getCors().getAllowedOrigins();
        String[] origins = corsAllowedOrigins.toArray(new String[corsAllowedOrigins.size()]);
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
