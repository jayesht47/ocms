package com.jayesh.ocms.controllers;


import com.jayesh.ocms.dto.UpdateUserDTO;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.exceptions.RegistrationValidationException;
import com.jayesh.ocms.services.UserService;
import com.jayesh.ocms.dto.RegisterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    private final Logger customLogger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User addUser(@RequestBody RegisterUser user) throws RegistrationValidationException {
        String currentLoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        customLogger.info("New user with userName : {} created by {}", user.getUserName(), currentLoggedInUserName);
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUser) throws NotFoundException {
        String currentLoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        customLogger.info("New user with userId : {} updated by {}", userId, currentLoggedInUserName);
        return userService.updateUser(updateUser, userId);
    }

    @GetMapping("/{userId}")
    public User getUserByUserId(@PathVariable("userId") String userId) throws NotFoundException {
        if (!getUserValidation(userId)) throw new AccessDeniedException("user does not have access to this resource");
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/currentUser")
    public User getCurrentLoggedInUser() throws NotFoundException {
        String currentLoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUserName(currentLoggedInUserName);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") String userId) throws NotFoundException {
        String currentLoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        customLogger.info("New user with userId : {} deleted by {}", userId, currentLoggedInUserName);
        userService.deleteUserByUserId(userId);
    }

    private boolean getUserValidation(String userId) throws NotFoundException {
        String currentLoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentLoggedInUser = userService.getUserByUserName(currentLoggedInUserName);
        if (userId.equals(currentLoggedInUser.getUserId())) return true;
        return SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
