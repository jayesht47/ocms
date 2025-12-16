package com.jayesh.ocms.services;

import com.jayesh.ocms.dto.UpdateUserDTO;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.exceptions.RegistrationValidationException;
import com.jayesh.ocms.repositories.UserRepository;
import com.jayesh.ocms.dto.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    private static final String NOT_FOUND = " not found";

    @Override
    public User createUser(RegisterUser user) throws RegistrationValidationException {

        userCreationValidation(user);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User newUser = new User();

        newUser.setUserName(user.getUserName());
        newUser.setDisplayName(user.getUserName());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRoles(List.of(user.getRole()));
        newUser.setEmail(user.getEmail());

        //basic params
        newUser.setEnabled(Boolean.TRUE);
        newUser.setAccountNonLocked(Boolean.TRUE);
        newUser.setCredentialsNonExpired(Boolean.TRUE);
        newUser.setAccountNonExpired(Boolean.TRUE);

        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User getUserByUserName(String userName) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new NotFoundException("User with userName : " + userName + NOT_FOUND);
    }

    @Override
    public User getUserByUserId(String userId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new NotFoundException("User with id : " + userId + NOT_FOUND);
    }


    @Override
    public void deleteUserByUserId(String userId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) userRepository.delete(userOptional.get());
        else throw new NotFoundException("User with id : " + userId + NOT_FOUND);
    }


    @Override
    public User updateUser(UpdateUserDTO updateUser, String userId) throws NotFoundException {
        User user = getUserByUserId(userId);

        if (updateUser.getDisplayName() != null) user.setDisplayName(updateUser.getDisplayName());
        if (updateUser.getEmail() != null) user.setEmail(updateUser.getEmail());
        if (updateUser.getUsername() != null) user.setUserName(updateUser.getUsername());

        userRepository.save(user);

        return user;
    }

    private void userCreationValidation(RegisterUser registerUser) throws RegistrationValidationException {
        String userName = registerUser.getUserName();
        String email = registerUser.getEmail();
        if (userRepository.findByUserName(userName).isPresent())
            throw new RegistrationValidationException("username already taken");
        if (userRepository.findByEmail(email).isPresent())
            throw new RegistrationValidationException("email already taken");
    }
}
