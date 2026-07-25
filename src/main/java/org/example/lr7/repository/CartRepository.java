package org.example.lr7.repository;

import org.example.lr7.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    public CartEntity findByCustomerId(Long userId);
}
