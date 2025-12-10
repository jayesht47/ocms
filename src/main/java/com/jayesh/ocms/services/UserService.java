package com.jayesh.ocms.services;

import com.jayesh.ocms.entities.User;
import com.jayesh.ocms.exceptions.NotFoundException;
import com.jayesh.ocms.vo.AuthUser;

public interface UserService {

    User createUser(AuthUser user);

    User getUserByUserName(String userName);

    User getUserByUserId(String userId) throws NotFoundException;

    boolean deleteUserByUserName(String userName);

}
