package com.voteva.gateway.security.config;

import com.voteva.gateway.security.filter.TokenAuthenticationFilter;
import com.voteva.gateway.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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
                .antMatchers("/api/users/login").permitAll()
                .anyRequest().authenticated();
    }
}
