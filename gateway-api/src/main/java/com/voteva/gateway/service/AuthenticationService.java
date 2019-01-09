package com.voteva.gateway.service;

import com.voteva.gateway.security.model.Authentication;
import com.voteva.gateway.web.to.in.LoginUserRequest;
import com.voteva.gateway.web.to.out.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    LoginInfo login(
            LoginUserRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse);

    Authentication getAuthentication(String token);
}
