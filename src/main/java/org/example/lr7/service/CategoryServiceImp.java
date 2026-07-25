package org.example.lr7.service;

import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.repository.CategoryRepository;
import org.example.lr7.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;
    private CategoryRepository repository;
    @Override
    public CategoryEntity createCategory(String name, Long userId) throws Exception {
        RestaurantEntity restaurant = restaurantService.getRestaurantByUserId(userId);
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setRestaurant(restaurant);


        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> findCategoryByRestaurantId(Long id) throws Exception {
        RestaurantEntity restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public CategoryEntity findCategoryById(Long id) throws Exception {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()){
            throw  new Exception("Category not found");
        }
        return optionalCategory.get();
    }
}
