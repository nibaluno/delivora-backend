package org.example.lr7.repository;

import org.example.lr7.model.entity.AddressEntity;
import org.example.lr7.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<OrderEntity,Long> {

    public List<OrderEntity> findByRestaurantId(Long restaurantId);
    public List<OrderEntity> findByCustomerId(Long userId);
}
