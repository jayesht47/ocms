package com.jayesh.ocms.services;

import com.jayesh.ocms.dto.UpdateUserDTO;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.dto.RegisterUser;
import com.jayesh.ocms.exceptions.RegistrationValidationException;

public interface UserService {

    User createUser(RegisterUser user) throws RegistrationValidationException;

    User getUserByUserName(String userName) throws NotFoundException;

    User getUserByUserId(String userId) throws NotFoundException;

    void deleteUserByUserId(String userId) throws NotFoundException;

    User updateUser(UpdateUserDTO updateUser, String userId) throws NotFoundException;
}
