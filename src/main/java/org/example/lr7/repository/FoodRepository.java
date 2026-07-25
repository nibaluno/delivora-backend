package org.example.lr7.repository;

import org.example.lr7.model.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM FoodEntity f WHERE f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
    List<FoodEntity> searchFood(@Param("keyword") String keyword);
}
