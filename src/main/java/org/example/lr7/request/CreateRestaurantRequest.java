package org.example.lr7.request;

import lombok.Data;
import org.example.lr7.model.entity.AddressEntity;
import org.example.lr7.model.entity.ContactInformationEntity;
import org.example.lr7.model.entity.RestaurantEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateRestaurantRequest  {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private AddressEntity address;
    private ContactInformationEntity contactInformation;
    private String openingHouse;
    private List<String> images;

}
