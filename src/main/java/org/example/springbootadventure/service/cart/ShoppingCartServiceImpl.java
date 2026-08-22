package org.example.springbootadventure.service.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.cart.AddToCartRequestDto;
import org.example.springbootadventure.dto.cart.ShoppingCartDto;
import org.example.springbootadventure.dto.cart.UpdateCartItemRequestDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.mapper.ShoppingCartMapper;
import org.example.springbootadventure.model.Book;
import org.example.springbootadventure.model.CartItem;
import org.example.springbootadventure.model.ShoppingCart;
import org.example.springbootadventure.model.User;
import org.example.springbootadventure.repository.book.BookRepository;
import org.example.springbootadventure.repository.cart.CartItemRepository;
import org.example.springbootadventure.repository.cart.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCartbyId(Long userId) {
        return shoppingCartMapper.toDto(getByUserId(userId));
    }

    @Override
    public ShoppingCartDto saveToCart(Long userId, AddToCartRequestDto requestDto) {
        ShoppingCart shoppingCart = getByUserId(userId);
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by id:" + requestDto.getBookId())
        );

        Optional<CartItem> optionalCartItem = shoppingCart.getCartItems().stream()
                .filter(t -> t.getBook().getId().equals(book.getId()))
                .findFirst();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setQuantity(requestDto.getQuantity());
            shoppingCart.getCartItems().add(newCartItem);
        }

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));

    }

    @Override
    @Transactional
    public ShoppingCartDto updateItemQuantity(Long userId, Long cartItemId,
                                              UpdateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getByUserId(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item with id: " + cartItemId
                                + " for shopping cart with id: " + shoppingCart.getId()));

        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteItemFromCart(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getByUserId(userId);
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item with id: " + cartItemId
                                + " for shopping cart with id: " + shoppingCart.getId()));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);

    }

    private ShoppingCart getByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart for user id: " + userId));
    }
}
