package org.example.lr7.service;

import org.example.lr7.dto.RestaurantDto;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.CreateRestaurantRequest;

import java.util.List;


public interface RestaurantService {

    public RestaurantEntity createRestaurant(CreateRestaurantRequest req, UserEntity user);

    public RestaurantEntity updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<RestaurantEntity> getAllRestaurants();
    public List<RestaurantEntity> searchRestaurants(String keyword);

    public RestaurantEntity findRestaurantById(Long id) throws Exception;

    public RestaurantEntity getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, UserEntity user) throws Exception;
    public RestaurantEntity updateRestaurantStatus(Long id) throws Exception;

}
