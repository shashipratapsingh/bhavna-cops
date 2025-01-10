package com.auth.controller;

import com.auth.model.request.UsersRequest;
import com.auth.model.response.UsersResponse;
import com.auth.security.JWT.JwtRequest;
import com.auth.security.JWT.JwtResponse;
import com.auth.security.service.AuthService;
import com.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsersController {

    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<UsersResponse> createUsers(@Valid @RequestBody UsersRequest usersRequest) {
        usersRequest.setPassword(passwordEncoder.encode(usersRequest.getPassword()));
        int userId = service.createUser(usersRequest);
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUserId(userId);
        usersResponse.setMessage("User Created");
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> tokenGeneration(@RequestBody JwtRequest jwtRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setEmail(jwtRequest.getEmail());
            jwtResponse.setToken(authService.GenerateToken(jwtRequest.getEmail()));
//            JwtResponse.builder().email(jwtRequest.getEmail()).token(authService.GenerateToken(jwtRequest.getEmail())).build()
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid request");
        }
    }
}