package com.voteva.gateway.web.controller;

import com.voteva.gateway.security.SecurityContextUtil;
import com.voteva.gateway.security.model.AuthenticationToken;
import com.voteva.gateway.security.model.Principal;
import com.voteva.gateway.service.AuthenticationService;
import com.voteva.gateway.service.QuizService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.LoginUserRequest;
import com.voteva.gateway.web.to.in.UserUidRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.LoginInfo;
import com.voteva.gateway.web.to.out.UserFullInfo;
import com.voteva.gateway.web.to.out.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final AuthenticationService authenticationService;
    private final QuizService quizService;

    @Autowired
    public UsersController(
            AuthenticationService authenticationService,
            QuizService quizService) {
        this.authenticationService = authenticationService;
        this.quizService = quizService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginInfo> login(
            @RequestBody @Valid LoginUserRequest loginUserRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        logger.debug("Getting request to login by user: {}", loginUserRequest.getUsername());

        return ResponseEntity.ok(authenticationService.login(loginUserRequest, httpServletRequest, httpServletResponse));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<LoginInfo> logout() {
        AuthenticationToken authentication = SecurityContextUtil.getAuthentication();

        logger.debug("Logout current user");

        authenticationService.logout(authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserInfo> me() {
        logger.debug("Getting info for current user");

        return ResponseEntity.ok(authenticationService.getUserInfo(SecurityContextUtil.getPrincipal()));
    }

    @GetMapping
    public ResponseEntity<PagedResult<UserFullInfo>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        logger.debug("Getting users for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(quizService.getUsers(page, size));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<UserFullInfo> getUserInfo(@PathVariable UUID uuid) {

        logger.debug("Getting user info by uid: {}", uuid);

        return ResponseEntity.ok(quizService.getUserByUid(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddUserInfo> addUser(@RequestBody @Valid AddUserRequest request) {

        logger.debug("Adding user: {}", request);

        return ResponseEntity.ok(quizService.addUser(request));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> setAdminGrants(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Blocking user with uid: {}", request.getUserUid());

        quizService.setAdminGrants(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/remove-admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> removeAdminGrants(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Unblocking user with uid: {}", request.getUserUid());

        quizService.removeAdminGrants(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/block", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> blockUser(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Blocking user with uid: {}", request.getUserUid());

        quizService.blockUser(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/unblock", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> unblockUser(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Unblocking user with uid: {}", request.getUserUid());

        quizService.unblockUser(request.getUserUid());
        return ResponseEntity.noContent().build();
    }
}
