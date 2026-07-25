package org.example.lr7.controller;

import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.CreateRestaurantRequest;
import org.example.lr7.response.MessageResponse;
import org.example.lr7.service.RestaurantService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<RestaurantEntity> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.createRestaurant(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantEntity> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.updateRestaurant(id, req);


        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Delete restaurant successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<RestaurantEntity> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<RestaurantEntity> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
