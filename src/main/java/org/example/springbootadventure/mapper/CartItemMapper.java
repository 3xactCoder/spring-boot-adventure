package org.example.springbootadventure.mapper;

import org.example.springbootadventure.config.MapperConfig;
import org.example.springbootadventure.dto.cart.CartItemDto;
import org.example.springbootadventure.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);
}
