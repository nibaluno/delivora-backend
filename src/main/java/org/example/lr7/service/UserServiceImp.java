package org.example.lr7.service;

import org.example.lr7.config.JwtProvider;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("user not found");
        }
        return user;
    }
}
