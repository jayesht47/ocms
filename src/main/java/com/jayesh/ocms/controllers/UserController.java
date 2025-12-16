package com.jayesh.ocms.controllers;


import com.jayesh.ocms.dto.UpdateUserDTO;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.exceptions.RegistrationValidationException;
import com.jayesh.ocms.services.UserService;
import com.jayesh.ocms.dto.RegisterUser;
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
    public User addUser(@RequestBody RegisterUser user) throws RegistrationValidationException {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUser) throws NotFoundException {
        return userService.updateUser(updateUser, userId);
    }

    @GetMapping("/{userId}")
    public User getUserByUserId(@PathVariable("userId") String userId) throws NotFoundException {
        return userService.getUserByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") String userId) throws NotFoundException {
        userService.deleteUserByUserId(userId);
    }

}
