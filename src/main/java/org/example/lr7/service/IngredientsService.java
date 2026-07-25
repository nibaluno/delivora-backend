package org.example.lr7.service;

import org.example.lr7.model.entity.IngredientsCategoryEntity;
import org.example.lr7.model.entity.IngredientsItemEntity;

import java.util.List;

public interface IngredientsService {
    public IngredientsCategoryEntity createIngredientCategory(String name, Long restaurantId) throws Exception;
    public IngredientsCategoryEntity findIngredientCategoryById(Long id) throws Exception;
    public List<IngredientsCategoryEntity> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItemEntity createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    // ИЗМЕНЕНО: возвращаем список Items (ингредиентов), а не категорий
    public List<IngredientsItemEntity> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItemEntity updateStock(Long id) throws Exception;
}
