package org.example.lr7.controller;


import org.example.lr7.model.entity.FoodEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.CreateFoodRequest;
import org.example.lr7.response.MessageResponse;
import org.example.lr7.service.FoodService;
import org.example.lr7.service.FoodServiceImp;
import org.example.lr7.service.RestaurantService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<FoodEntity> createFood(@RequestBody CreateFoodRequest req,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantEntity restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        FoodEntity food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("food has been deleted");
        return new ResponseEntity<>(messageResponse , HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodEntity> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        FoodEntity food =  foodService.updateAvailibilityStatus(id);

        return new ResponseEntity<>(food , HttpStatus.CREATED);

    }
}
