package com.auth.security.service;

import com.auth.entity.Users;
import com.auth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BhavnaCopsUserService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findByEmail(username);
        return users.map(BhavanaCorpUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found for email :" + username));
    }
}