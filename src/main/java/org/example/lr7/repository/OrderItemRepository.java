package org.example.lr7.repository;

import org.example.lr7.model.entity.OrderEntity;
import org.example.lr7.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {

}
