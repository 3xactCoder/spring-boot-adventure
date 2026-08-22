package org.example.springbootadventure.mapper;

import org.example.springbootadventure.config.MapperConfig;
import org.example.springbootadventure.dto.cart.ShoppingCartDto;
import org.example.springbootadventure.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
