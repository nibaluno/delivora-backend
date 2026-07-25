package org.example.lr7.controller;


import org.example.lr7.model.entity.CartEntity;
import org.example.lr7.model.entity.CartItemEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.request.AddCartItemRequest;
import org.example.lr7.request.UpdateCartItemRequest;
import org.example.lr7.service.CartService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItemEntity> addItemToCart(@RequestBody AddCartItemRequest req,
                                                              @RequestHeader("Authorization") String jwt) throws Exception {

        CartItemEntity cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);

    }



    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItemEntity> updateCartItemQuantity(
            @RequestBody UpdateCartItemRequest req,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {

        CartItemEntity cartItem = cartService.updateCartItemQuantity(req.getCartItemId(),  req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);

    }


    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<CartEntity> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartEntity cart = cartService.removeCartItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }


    @PutMapping("/cart/clear")
    public ResponseEntity<CartEntity> clearCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        CartEntity cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @GetMapping("/cart")
    public ResponseEntity<CartEntity> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        CartEntity cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
