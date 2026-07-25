package org.example.lr7.request;

import lombok.Data;

@Data
public class IngrediantRequest {

    private String name;
    private Long categoryId;
    private Long restaurantId;
}
