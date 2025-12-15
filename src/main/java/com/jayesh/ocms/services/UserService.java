package com.jayesh.ocms.services;

import com.jayesh.ocms.dto.UpdateUserDTO;
import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.dto.AuthUser;

public interface UserService {

    User createUser(AuthUser user);

    User getUserByUserName(String userName) throws NotFoundException;

    User getUserByUserId(String userId) throws NotFoundException;

    boolean deleteUserByUserName(String userName);

    User updateUser(UpdateUserDTO updateUser, String userId) throws NotFoundException;
}
