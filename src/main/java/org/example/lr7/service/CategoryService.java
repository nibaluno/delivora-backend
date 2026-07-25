package org.example.lr7.service;

import org.example.lr7.model.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    public CategoryEntity createCategory(String name, Long userId) throws Exception;

    public List<CategoryEntity> findCategoryByRestaurantId(Long id) throws Exception;
    public CategoryEntity findCategoryById(Long id) throws Exception;
}
