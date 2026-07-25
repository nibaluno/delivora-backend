package org.example.lr7.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders") //?
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //автоматически выбирает стратегию  в зависимости от бд (автоинкремент
    // последовательности или если не поддерживает ни то ни другое
    private Long id;

    @ManyToOne
    private UserEntity customer;

    @JsonIgnore
    @ManyToOne
    private RestaurantEntity restaurant;


    private Long totalAmount;
    private String orderStatus; //вынести в перечесление?
    private Date orderDate;

    @ManyToOne
    private AddressEntity deliveryAddress;

    @OneToMany
    private List<OrderItemEntity> items;

    //private PaymentEntity payment;
    private int totalItem;
    private Long  totalPrice;



    // private Date setCreatedAt;
}

