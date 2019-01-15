package com.voteva.gateway.security;

import com.voteva.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final TokenConfig tokenConfig;

    @Autowired
    public SecurityTokenAdapter(
            AuthenticationService authenticationService,
            TokenConfig tokenConfig) {

        this.authenticationService = authenticationService;
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()))
                .and()
                .addFilterAfter(new TokenAuthenticationFilter(authenticationService, tokenConfig), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(
                        "/api/v1/users/login",
                        "/api/v1/oauth",
                        "/api/v1/oauth/token",
                        "/api/v1/oauth/refresh")
                .permitAll()
                .anyRequest().authenticated();
    }
}
