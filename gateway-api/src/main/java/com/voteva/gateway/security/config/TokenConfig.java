package com.voteva.gateway.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenConfig {

    @Value("${security.token.header:Authorization}")
    private String header;

    @Value("${security.token.prefix:Bearer }")
    private String prefix;

    @Value("#{new Long('${security.token.ttl.seconds}')}")
    private long tokenTtlSeconds;

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public long getTokenTtlSeconds() {
        return tokenTtlSeconds;
    }
}
