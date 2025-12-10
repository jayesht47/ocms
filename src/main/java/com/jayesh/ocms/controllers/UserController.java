package com.jayesh.ocms.controllers;


import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.services.UserService;
import com.jayesh.ocms.vo.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;


    @PostMapping
    public User addUser(@RequestBody AuthUser user) {
        return userService.createUser(user);
    }


    @GetMapping("/{userId}")
    public User getUserByUserId(@PathVariable("userId") String userId) throws NotFoundException {
        return userService.getUserByUserId(userId);
    }

}
