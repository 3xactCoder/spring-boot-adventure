package org.example.springbootadventure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.cart.AddToCartRequestDto;
import org.example.springbootadventure.dto.cart.ShoppingCartDto;
import org.example.springbootadventure.dto.cart.UpdateCartItemRequestDto;
import org.example.springbootadventure.model.User;
import org.example.springbootadventure.service.cart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user's shopping cart",
            description = "Retrieve the shopping cart of the logged-in user")
    ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCartbyId(user.getId());
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add book to cart",
             description = "Add a book item to the logged-in user's shopping cart")
    ShoppingCartDto addItemShoppingCart(@RequestBody @Valid AddToCartRequestDto requestDto,
                                        Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.saveToCart(user.getId(),requestDto);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update book quantity",
             description = "Update the quantity of a book item in the shopping cart")
    ShoppingCartDto updateBookQuantity(Authentication authentication,
                                        @PathVariable Long cartItemId,
                                        @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateItemQuantity(user.getId(),cartItemId,requestDto);

    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove item from cart",
             description = "Remove a book item from the shopping cart")
    void deleteItemFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteItemFromCart(user.getId(), cartItemId);

    }
}
