package org.example.lr7.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UserEntity owner;

    private String name;
    private String description;
    private String cuisineType;

    @OneToOne
    private  AddressEntity address;


    @Embedded
    /*
    * Аннотация @Embedded в Java (JPA/Hibernate) используется для встраивания
    * полей одного Java-класса (@Embeddable)
    * непосредственно в таблицу другого класса-сущности (@Entity).
    * */
    private  ContactInformationEntity  contactInformation;

    private String openingHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true) //Я не владею этой связью.
    // Смотри в поле restaurant в классе OrderEntity — там лежит внешний ключ
    //cascade = CascadeType.ALL Когда я выполняю операцию с родителем (RestaurantEntity),
    // выполни ТУ ЖЕ САМУЮ операцию со всеми дочерними сущностями (OrderEntity)
    private List<OrderEntity> orders = new ArrayList<>();


    @ElementCollection
    @Column(length = 1000)
    private List<String> images = new ArrayList<>();

    private LocalDateTime registrationDate;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",  cascade = CascadeType.ALL)
    private List<FoodEntity> foods = new ArrayList<>();


    public void getName(String name) {/// /////////
        this.name = name;
    }
}
