package com.jayesh.ocms.controllers;

import com.jayesh.ocms.dto.LoginUserResponse;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.services.JwtService;
import com.jayesh.ocms.services.UserService;
import com.jayesh.ocms.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody LoginUser loginUser) throws NotFoundException {

        User user = userService.getUserByUserName(loginUser.getUserName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean match = bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword());
        if (!match) throw new BadCredentialsException("Incorrect username password");
        String token = jwtService.createToken(user.getUsername());
        LoginUserResponse response = new LoginUserResponse();
        response.setError(Boolean.FALSE);
        response.setToken(token);
        return response;
    }

}
