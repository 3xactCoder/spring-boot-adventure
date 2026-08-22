package org.example.springbootadventure.dto.order;

import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequestDto {
    @NotBlank
    private String shippingAddress;
}
