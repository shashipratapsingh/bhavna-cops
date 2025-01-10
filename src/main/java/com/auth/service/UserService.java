package com.auth.service;

import com.auth.model.request.UsersRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    int createUser(UsersRequest usersRequest);
}