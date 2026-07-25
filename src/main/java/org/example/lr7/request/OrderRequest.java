package org.example.lr7.request;

import lombok.Data;
import org.example.lr7.model.entity.AddressEntity;

@Data
public class OrderRequest {
    private Long restaurantId;
    private AddressEntity deliveryAdress;


}
