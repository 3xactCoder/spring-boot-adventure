package org.example.springbootadventure.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequestDto {
    @NotNull
    @Min(1)
    private Long bookId;

    @Min(1)
    private int quantity;
}

