package org.example.lr7.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<IngredientsItemEntity> ingredientsItemEntities;
}
