package org.example.lr7.service;

import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.FoodEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.repository.FoodRepository;
import org.example.lr7.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FoodServiceImp implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public FoodEntity createFood(CreateFoodRequest req, CategoryEntity category, RestaurantEntity restaurant) {

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFoodCategory(category);
        foodEntity.setRestaurant(restaurant);
        foodEntity.setDescription(req.getDescription());
        foodEntity.setImages(req.getImages());
        foodEntity.setName(req.getName());
        foodEntity.setPrice(req.getPrice());
        foodEntity.setIngredients(req.getIngredients());
        foodEntity.setSeasonal(req.isSeasonal());
        foodEntity.setVegetarian(req.isVegetarin());
        FoodEntity savedFood =  foodRepository.save(foodEntity);
        restaurant.getFoods().add(foodEntity);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        FoodEntity food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);


    }

    @Override
    public List<FoodEntity> getRestaurantsFood(Long restaurantId,
                                               boolean isVegitarian,
                                               boolean isNonveg,
                                               boolean isSeasinal, String foodCategory) {

        List<FoodEntity> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVegitarian) {
            foods = filterByVegetarian(foods, isVegitarian);
        }
        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }

        if (isSeasinal) {
            foods = filterBySeasinal(foods, isSeasinal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<FoodEntity> filterByCategory(List<FoodEntity> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());


    }

    private List<FoodEntity> filterBySeasinal(List<FoodEntity> foods, boolean isSeasinal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasinal).collect(Collectors.toList());

    }

    private List<FoodEntity> filterByNonveg(List<FoodEntity> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());

    }

    private List<FoodEntity> filterByVegetarian(List<FoodEntity> foods, boolean isVegitarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegitarian).collect(Collectors.toList());
    }

    @Override
    public List<FoodEntity> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public FoodEntity findFoodById(Long foodId) throws Exception {
        Optional<FoodEntity> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("Food not found");
        }
        return optionalFood.get();
    }

    @Override
    public FoodEntity updateAvailibilityStatus(Long foodId) throws Exception {
        FoodEntity food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);

    }
}
