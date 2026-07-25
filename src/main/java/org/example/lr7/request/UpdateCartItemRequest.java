package org.example.lr7.request;


import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long cartItemId;
    private int quantity;

}
