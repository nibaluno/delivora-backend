package org.example.lr7.repository;

import org.example.lr7.model.entity.IngredientsCategoryEntity;
import org.example.lr7.model.entity.IngredientsItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItemEntity, Long> {
    List<IngredientsItemEntity> findByRestaurantId(Long id);
}
