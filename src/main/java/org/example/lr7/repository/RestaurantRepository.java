package org.example.lr7.repository;

import org.example.lr7.model.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {


    @Query("SELECT r FROM RestaurantEntity r WHERE lower(r.name) LIKE lower(concat('%', :query, '%'))" +
            " OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<RestaurantEntity> findBySearchQuery(String query);
    RestaurantEntity findByOwnerId(Long userId);
}
