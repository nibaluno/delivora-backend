package org.example.lr7.service;

import org.example.lr7.dto.RestaurantDto;
import org.example.lr7.model.entity.AddressEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.repository.AddressRepository;
import org.example.lr7.repository.RestaurantRepository;
import org.example.lr7.repository.UserRepository;
import org.example.lr7.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RestaurantEntity createRestaurant(CreateRestaurantRequest req, UserEntity user) {

        AddressEntity address = addressRepository.save(req.getAddress());

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.getName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHouse());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantEntity updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        RestaurantEntity restaurant = findRestaurantById(restaurantId);

        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if(restaurant.getName() != null) {
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        RestaurantEntity restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<RestaurantEntity> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<RestaurantEntity> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public RestaurantEntity findRestaurantById(Long id) throws Exception {
        Optional<RestaurantEntity> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public RestaurantEntity getRestaurantByUserId(Long userId) throws Exception {
        RestaurantEntity restaurant =  restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, UserEntity user) throws Exception {
        RestaurantEntity restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorites = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorites = true;
                break;
            }
        }

        if (!isFavorites) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public RestaurantEntity updateRestaurantStatus(Long id) throws Exception {
        RestaurantEntity restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
