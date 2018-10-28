package com.voteva.gateway.web.controller;

import com.voteva.gateway.service.UsersService;
import com.voteva.gateway.web.to.common.UserInfo;
import com.voteva.gateway.web.to.in.AddUserRequest;
import com.voteva.gateway.web.to.in.BlockUserRequest;
import com.voteva.gateway.web.to.in.UnblockUserRequest;
import com.voteva.gateway.web.to.in.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
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

    /*@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserInfo>> getUsers(SpringDataWebProperties.Pageable pageable) {
        logger.debug("Getting users for page={}", pageable);

        return ResponseEntity.ok(usersService.getUsers(pageable));
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ResponseEntity<UserInfo> getUserByUid(@RequestParam("user_uid") UUID userUid) {
        logger.debug("Getting user info by uid={}", userUid);

        return ResponseEntity.ok(usersService.getUserByUid(userUid));
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserInfo> addUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        logger.debug("Adding user={}", addUserRequest);

        return ResponseEntity.ok(usersService.addUser(addUserRequest));
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserInfo> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        logger.debug("Updating user={}", updateUserRequest);

        return ResponseEntity.ok(usersService.updateUser(updateUserRequest));
    }

    @RequestMapping(path = "/block", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> blockUser(@RequestBody @Valid BlockUserRequest blockUserRequest) {
        logger.debug("Blocking user with uid={}", blockUserRequest.getUserUid());

        usersService.blockUser(blockUserRequest.getUserUid());

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/unblock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> unblockUser(@RequestBody @Valid UnblockUserRequest unblockUserRequest) {
        logger.debug("Unblocking user with uid={}", unblockUserRequest.getUserUid());

        usersService.unblockUser(unblockUserRequest.getUserUid());

        return ResponseEntity.noContent().build();
    }*/
}
