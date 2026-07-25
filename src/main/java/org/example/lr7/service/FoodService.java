package org.example.lr7.service;

import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.FoodEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public FoodEntity createFood(CreateFoodRequest req, CategoryEntity category , RestaurantEntity restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<FoodEntity> getRestaurantsFood(Long restaurantId,
                                               boolean isVegitarian,
                                               boolean isNonveg,
                                               boolean isSeasinal,
                                               String foodCategory);

    public List<FoodEntity> searchFood(String keyword);

    public FoodEntity findFoodById(Long foodId) throws Exception;

    public FoodEntity updateAvailibilityStatus(Long foodId) throws Exception;

}
