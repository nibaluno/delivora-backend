package org.example.lr7.service;

import org.example.lr7.model.entity.CartEntity;
import org.example.lr7.model.entity.CartItemEntity;
import org.example.lr7.request.AddCartItemRequest;

public interface CartService {

    public CartItemEntity addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItemEntity updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public CartEntity removeCartItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotals(CartEntity cart) throws Exception;

    public CartEntity findCartById(Long id) throws Exception;
    public CartEntity findCartByUserId(Long userId) throws Exception;

    public CartEntity clearCart(Long userId) throws Exception;
}
