package org.example.lr7.controller;


import org.example.lr7.dto.RestaurantDto;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.CreateRestaurantRequest;
import org.example.lr7.service.RestaurantService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reataurans")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<RestaurantEntity>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword

    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<RestaurantEntity> restaurant = restaurantService.searchRestaurants(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantEntity>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<RestaurantEntity> restaurant = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantEntity> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorites(id, user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
