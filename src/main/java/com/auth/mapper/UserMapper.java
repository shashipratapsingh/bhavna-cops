package com.auth.mapper;

import com.auth.entity.Users;
import com.auth.model.request.UsersRequest;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    public  static Users toUser(UsersRequest usersRequest){
        Users users = new Users();
        BeanUtils.copyProperties(usersRequest, users);
        return users;
    }
}