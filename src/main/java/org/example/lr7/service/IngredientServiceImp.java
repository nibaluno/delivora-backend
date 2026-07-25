package org.example.lr7.service;

import org.example.lr7.model.entity.IngredientsCategoryEntity;
import org.example.lr7.model.entity.IngredientsItemEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.repository.IngredientCategoryRepository;
import org.example.lr7.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService{
    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategoryEntity createIngredientCategory(String name, Long restaurantId) throws Exception {
        RestaurantEntity restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsCategoryEntity category = new IngredientsCategoryEntity();
        category.setName(name);
        category.setRestaurant(restaurant);


        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientsCategoryEntity findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientsCategoryEntity> optional = ingredientCategoryRepository.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Ingredient Category Not Found");
        }
        return optional.get();
    }

    @Override
    public List<IngredientsCategoryEntity> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItemEntity createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        RestaurantEntity restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientsItemEntity item = new IngredientsItemEntity();
        IngredientsCategoryEntity category = findIngredientCategoryById(categoryId);

        item.setRestaurant(restaurant);
        item.setName(ingredientName);
        item.setCategory(category);

        IngredientsItemEntity itemEntity = ingredientItemRepository.save(item);
        category.getIngredientsItemEntities().add(itemEntity);////////
        return itemEntity;
    }

    @Override
    public List<IngredientsItemEntity> findRestaurantIngredients(Long restaurantId) throws Exception {
        // ИЗМЕНЕНО: используем ingredientItemRepository вместо ingredientCategoryRepository
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItemEntity updateStock(Long id) throws Exception {
        Optional<IngredientsItemEntity> optional = ingredientItemRepository.findById(id);

        if(optional.isEmpty()){
            throw new Exception("Ingredient Item Not Found");
        }

        IngredientsItemEntity ingredientsItem = optional.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
