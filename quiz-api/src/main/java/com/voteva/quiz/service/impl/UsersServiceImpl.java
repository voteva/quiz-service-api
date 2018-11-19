package com.voteva.quiz.service.impl;

import com.voteva.quiz.exception.NotFoundUserException;
import com.voteva.quiz.model.entity.UserEntity;
import com.voteva.quiz.repository.UsersRepository;
import com.voteva.quiz.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable) {
        return usersRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public UserEntity getUser(UUID userUid) {
        return usersRepository.findByUserUid(userUid)
                .orElseThrow(() -> new NotFoundUserException("Not found user with uid=" + userUid));
    }

    @Override
    public UserEntity addUser(UserEntity entity) {
        return usersRepository.save(entity);
    }

    @Override
    public void updateUser(UserEntity entity) {

    }

    @Override
    public void setAdmin(UUID userUid) {
        updateUserGrantsInternal(userUid, true);
    }

    @Override
    public void resetAdmin(UUID userUid) {
        updateUserGrantsInternal(userUid, false);
    }

    @Override
    public void blockUser(UUID userUid) {
        updateUserStatusInternal(userUid, true);
    }

    @Override
    public void unblockUser(UUID userUid) {
        updateUserStatusInternal(userUid, false);
    }

    private void updateUserGrantsInternal(UUID userUid, boolean isAdmin) {
        usersRepository.save(getUser(userUid).setAdmin(isAdmin));
    }

    private void updateUserStatusInternal(UUID userUid, boolean isBlocked) {
        usersRepository.save(getUser(userUid).setBlocked(isBlocked));
    }
}
