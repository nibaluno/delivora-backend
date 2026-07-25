package org.example.lr7.controller;

import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.OrderEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.OrderRequest;
import org.example.lr7.service.OrderService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/oder")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderRequest req,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        OrderEntity order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @GetMapping("/oder/user")
    public ResponseEntity<List<OrderEntity>> getOrderHistory(
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<OrderEntity> orders = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }
}
