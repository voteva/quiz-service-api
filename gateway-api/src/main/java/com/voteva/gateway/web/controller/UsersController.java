package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.UsersService;
import com.voteva.gateway.web.to.common.PagedResult;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.UserUidRequest;
import com.voteva.gateway.web.to.out.AddUserInfo;
import com.voteva.gateway.web.to.out.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResult<UserInfo>> getUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        logger.debug("Getting users for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(usersService.getUsers(page, size));
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable UUID uuid) {

        logger.debug("Getting user info by uid: {}", uuid);

        return ResponseEntity.ok(usersService.getUserByUid(uuid));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddUserInfo> addUser(@RequestBody @Valid AddUserRequest request) {

        logger.debug("Adding user: {}", request);

        return ResponseEntity.ok(usersService.addUser(request));
    }

    @RequestMapping(path = "/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> setAdminGrants(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Blocking user with uid: {}", request.getUserUid());

        usersService.setAdminGrants(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/remove-admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> removeAdminGrants(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Unblocking user with uid: {}", request.getUserUid());

        usersService.removeAdminGrants(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/block", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> blockUser(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Blocking user with uid: {}", request.getUserUid());

        usersService.blockUser(request.getUserUid());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/unblock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> unblockUser(@RequestBody @Valid UserUidRequest request) {

        logger.debug("Unblocking user with uid: {}", request.getUserUid());

        usersService.unblockUser(request.getUserUid());
        return ResponseEntity.noContent().build();
    }
}
