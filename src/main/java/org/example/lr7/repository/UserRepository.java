package org.example.lr7.repository;

import org.example.lr7.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByEmail(String username);
}
