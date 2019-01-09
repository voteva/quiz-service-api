package com.voteva.gateway.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private Principal principal;

    public AuthenticationToken(
            String token,
            Principal principal,
            Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        setAuthenticated(true);

        this.token = token;
        this.principal = principal;
    }

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getToken() {
        return token;
    }
}
