package org.example.lr7.service;

import org.example.lr7.model.entity.CartEntity;
import org.example.lr7.model.entity.CartItemEntity;
import org.example.lr7.model.entity.FoodEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.repository.CartItemRepository;
import org.example.lr7.repository.CartRepository;
import org.example.lr7.repository.FoodRepository;
import org.example.lr7.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;



    @Override
    public CartItemEntity addItemToCart(AddCartItemRequest req, String jwt) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        FoodEntity food = foodService.findFoodById(req.getFoodId());

        CartEntity cart = cartRepository.findByCustomerId(user.getId());

        for(CartItemEntity cartItem : cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                cartItem.setQuantity(newQuantity);

                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }


        CartItemEntity newCartItem = new CartItemEntity();

        newCartItem.setFood(food);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setCart(cart);
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItemEntity savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItem().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItemEntity updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItemEntity> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("cartItem not found");
        }

        CartItemEntity cartItemEntity = cartItem.get();
        cartItemEntity.setQuantity(quantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getFood().getPrice() * quantity);
         return cartItemRepository.save(cartItemEntity);

    }

    @Override
    public CartEntity removeCartItemFromCart(Long cartItemId, String jwt) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        CartEntity cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItemEntity> cartItem = cartItemRepository.findById(cartItemId);

        if(cartItem.isEmpty()){
            throw new Exception("cartItem not found");
        }

        CartItemEntity cartItemEntity = cartItem.get();
        cart.getItem().remove(cartItemEntity);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(CartEntity cart) throws Exception {

        Long total = 0L;
        for (CartItemEntity cartItem : cart.getItem()){
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public CartEntity findCartById(Long id) throws Exception {

        Optional<CartEntity> cartItem = cartRepository.findById(id);
        if(cartItem.isEmpty()){
            throw new Exception("cartItem not found with id " + id);
        }
        return cartItem.get();
    }

    @Override
    public CartEntity findCartByUserId(Long userId) throws Exception {


        //UserEntity user = userService.findUserByJwtToken(jwt);

        CartEntity cart =  cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public CartEntity clearCart(Long userId) throws Exception {

       // UserEntity user = userService.findUserByJwtToken(jwt);
        CartEntity cart = findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
