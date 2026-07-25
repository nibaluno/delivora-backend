package org.example.lr7.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lr7.model.enums.USER_ROLE;
import org.example.lr7.dto.RestaurantDto;

import java.util.ArrayList;
import java.util.List;

@Entity // для сущности создасся таблица в бд
@Data //геттеры сеттеры toString(), equals() и hashCode() конструктор с обязательными параметрами @RequiredArgsConstructor
@AllArgsConstructor //генерирует конструктор со всеми полями (включая id)
@NoArgsConstructor //генерирует пустой конструктор (требуется JPA/Hibernate).
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //говорит Hibernate автоматически генерировать значение ID.
    private Long id; // класс обертка может быть null
    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @JsonIgnore //чтобы избежать цикла?
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer") // одна запись в текущей таблице (например, UserEntity)
    // связана с несколькими записями в другой таблице (OrderEntity
    private List<OrderEntity> orders = new ArrayList<>();

    @ElementCollection //позволит нам хранить не одно поле, а много значений
    private List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Когда я делаю что-то с родителем (UserEntity),
    // сделай ТО ЖЕ САМОЕ со всеми его дочерними сущностями (OrderEntity)
    // Та, у которой есть @JoinColumn (или аннотация @ManyToOne/@OneToOne без mappedBy

    //втоматически удаляет дочернюю сущность, если она больше не связана с родителем
    private List<AddressEntity> addresses = new ArrayList<>();

}
