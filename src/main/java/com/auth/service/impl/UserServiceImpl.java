package com.auth.service.impl;

import com.auth.entity.Users;
import com.auth.mapper.UserMapper;
import com.auth.model.request.UsersRequest;
import com.auth.repository.UsersRepository;
import com.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository repository;

    @Override
    public int createUser(UsersRequest usersRequest) {
        Users users = UserMapper.toUser(usersRequest);
        users = repository.save(users);
        return users.getId();
    }
}
