package org.example.lr7.controller;

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
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;



    @GetMapping("/oder/restaurant/{id}")
    public ResponseEntity<List<OrderEntity>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<OrderEntity> orders = orderService.getRestaurantsOrders(id,order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }
    @PostMapping("/oder/{orderId}/{orderStatus}")
    public ResponseEntity<OrderEntity> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        OrderEntity orders = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

}
