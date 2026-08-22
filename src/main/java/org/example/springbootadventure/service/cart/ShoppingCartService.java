package org.example.springbootadventure.service.cart;

import org.example.springbootadventure.dto.cart.AddToCartRequestDto;
import org.example.springbootadventure.dto.cart.ShoppingCartDto;
import org.example.springbootadventure.dto.cart.UpdateCartItemRequestDto;
import org.example.springbootadventure.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    ShoppingCartDto getShoppingCartbyId(Long userId);

    ShoppingCartDto saveToCart(Long userId, AddToCartRequestDto requestDto);

    ShoppingCartDto updateItemQuantity(Long userId, Long cartItemId,
                                       UpdateCartItemRequestDto requestDto);

    void deleteItemFromCart(Long userId, Long cartItemId);

    void registerNewShoppingCart(User user);
}
