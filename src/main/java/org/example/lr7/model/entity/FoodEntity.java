package org.example.lr7.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private CategoryEntity  foodCategory;

    @Column(length = 10000)
    @ElementCollection
    private List<String> images;

    private boolean available;

    @ManyToOne // Много блюд → Один ресторан
    private RestaurantEntity restaurant;

    private boolean isSeasonal;
    private boolean isVegetarian;


    @ManyToMany
    //ОДНО блюдо → МНОГО ингредиентов
    //ОДИН ингредиент → МНОГО блюд
    private List<IngredientsItemEntity> ingredients = new ArrayList<>();

    private Data creationDate;
}
