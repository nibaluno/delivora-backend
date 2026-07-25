package org.example.lr7.repository;

import org.example.lr7.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    public List<CategoryEntity> findByRestaurantId(Long id);
}
