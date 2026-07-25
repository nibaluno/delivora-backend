package org.example.lr7.service;

import org.example.lr7.model.entity.*;
import org.example.lr7.repository.*;
import org.example.lr7.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;



    @Override
    public OrderEntity createOrder(OrderRequest order, UserEntity user) throws Exception {

        AddressEntity shippingAddress = order.getDeliveryAdress();
        AddressEntity savedAddress = addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        RestaurantEntity restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setRestaurant(restaurant);
        orderEntity.setDeliveryAddress(shippingAddress);
        orderEntity.setCustomer(user);
        orderEntity.setOrderDate(new Date());
        orderEntity.setOrderStatus("PENDING");

        CartEntity cart = cartService.findCartByUserId(user.getId());

        List<OrderItemEntity> orderItems = new ArrayList<>();

        for(CartItemEntity cartItem : cart.getItem()){
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItemEntity saveOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(saveOrderItem);

        }

        orderEntity.setItems(orderItems);
        orderEntity.setTotalPrice(cart.getTotal());
        OrderEntity savedOrder =  orderRepository.save(orderEntity);
        restaurant.getOrders().add(orderEntity);
        return orderEntity;
    }

    @Override
    public OrderEntity updateOrder(Long orderId, String orderStatus) throws Exception {
        OrderEntity order = findOrderById(orderId);

        if(order.getOrderStatus().equals("OUT_FOR_DELIVERY")
                || order.getOrderStatus().equals("DELIVERED")
                || order.getOrderStatus().equals("COMPLETED")
                || order.getOrderStatus().equals("PENDING")
        ){

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("please input valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        OrderEntity order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderEntity> getUserOrders(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<OrderEntity> getRestaurantsOrders(Long restaurantId, String orderStatus) throws Exception {
       List<OrderEntity> orders =  orderRepository.findByRestaurantId(restaurantId);
       if(orderStatus != null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
       }
       return orders;
    }

    @Override
    public OrderEntity findOrderById(Long orderId) throws Exception {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if(orderEntity.isEmpty()){
            throw new Exception("order not found");
        }
        return orderEntity.get();
    }
}
