package com.voteva.gateway.web.controller;

import com.voteva.gateway.config.OAuthConfig;
import com.voteva.gateway.security.SecurityContextUtil;
import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.service.OAuth2Service;
import com.voteva.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.voteva.gateway.web.to.in.OAuthTokenRequest;
import com.voteva.gateway.web.to.out.OAuthCodeResponse;
import com.voteva.gateway.web.to.out.OAuthTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/oauth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);
    private static final String AUTHORIZE_CALLBACK_URI_TEMPLATE = "%s://%s/callback?code=%s";

    private final OAuthConfig oAuthConfig;
    private final OAuth2Service oAuth2Service;

    @Autowired
    public OAuthController(
            OAuthConfig oAuthConfig,
            OAuth2Service oAuth2Service) {
        this.oAuthConfig = oAuthConfig;
        this.oAuth2Service = oAuth2Service;
    }

    @GetMapping
    public RedirectView requestAuthorization(
            @RequestParam("clientId") String clientId,
            @RequestParam("redirectUri") String redirectUri) {

        logger.debug("Getting request for authorization from client {}", clientId);

        return new RedirectView(String.format(oAuthConfig.getRedirectOauthUriTemplate(), clientId, redirectUri));
    }

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("clientId") String clientId,
            @RequestParam("redirectUri") String redirectUri,
            HttpServletRequest httpServletRequest) {

        logger.debug("Authorizing client {}", clientId);

        AuthenticationToken authentication = SecurityContextUtil.getAuthentication();
        OAuthCodeResponse codeResponse = oAuth2Service.authorize(clientId, authentication);

        return String.format(AUTHORIZE_CALLBACK_URI_TEMPLATE,
                httpServletRequest.getScheme(), redirectUri, codeResponse.getAuthorizationCode());
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuthTokenResponse> getOAuthToken(@RequestBody @Valid OAuthTokenRequest request) {

        logger.debug("Getting OAuth token for client {}", request.getClientId());

        return ResponseEntity.ok(oAuth2Service.getToken(request));
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuthTokenResponse> refreshOAuthToken(@RequestBody @Valid OAuthRefreshTokenRequest request) {

        logger.debug("Refreshing OAuth token for client {}", request.getClientId());

        return ResponseEntity.ok(oAuth2Service.refreshToken(request));
    }
}
