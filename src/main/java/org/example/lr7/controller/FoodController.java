package org.example.lr7.controller;

import org.example.lr7.model.entity.FoodEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.CreateFoodRequest;
import org.example.lr7.service.FoodService;
import org.example.lr7.service.RestaurantService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodEntity>> searchFood(@RequestParam String name,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<FoodEntity> food = foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodEntity>> getRestaurantFood(@RequestParam boolean vagitarian,
                                                              @RequestParam boolean seasonal,
                                                              @RequestParam boolean nonveg,
                                                              @PathVariable Long restaurantId,
                                                              @RequestParam(required = false) String food_category,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<FoodEntity> food = foodService.getRestaurantsFood(restaurantId,vagitarian, nonveg, seasonal, food_category);
        return new ResponseEntity<>(food, HttpStatus.OK);

    }
}
