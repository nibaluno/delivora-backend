package org.example.lr7.service;

import org.example.lr7.model.entity.UserEntity;

public interface UserService {
    public UserEntity findUserByJwtToken(String jwt) throws Exception;

    public UserEntity findUserByEmail(String email) throws Exception;
}
