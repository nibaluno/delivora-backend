package org.example.lr7.request;


import lombok.Data;
import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.IngredientsItemEntity;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long Price;

    private CategoryEntity category;
    private List<String> images;

    private Long RestaurantId;
    private boolean vegetarin;
    private boolean seasonal;

    private List<IngredientsItemEntity> ingredients;
}
