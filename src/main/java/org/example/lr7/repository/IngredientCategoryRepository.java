package org.example.lr7.repository;

import org.example.lr7.model.entity.IngredientsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategoryEntity, Long> {

    List<IngredientsCategoryEntity> findByRestaurantId(Long id);
}
