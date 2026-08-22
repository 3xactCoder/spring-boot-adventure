package org.example.springbootadventure.repository.cart;

import java.util.Optional;
import org.example.springbootadventure.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);
}
