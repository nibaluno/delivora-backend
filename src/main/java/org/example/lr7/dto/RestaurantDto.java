package org.example.lr7.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
/*
* @Embeddable — это аннотация JPA (Java Persistence API),
*  помечающая класс как «встраиваемый». Такой класс не имеет собственной таблицы в БД,
* а его поля становятся частью таблицы основной сущности (@Entity), в которую он внедрен
*  с помощью аннотации @Embedded.
* */
public class RestaurantDto {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;
}
