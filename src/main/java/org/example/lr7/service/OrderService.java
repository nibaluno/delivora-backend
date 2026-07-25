package org.example.lr7.service;

import org.example.lr7.model.entity.OrderEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public OrderEntity createOrder(OrderRequest order, UserEntity user) throws Exception;

    public OrderEntity updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<OrderEntity>  getUserOrders(Long userId) throws Exception;
    public List<OrderEntity>  getRestaurantsOrders(Long restaurantId, String orderStatus) throws Exception;

    public OrderEntity findOrderById(Long orderId) throws Exception;
}
