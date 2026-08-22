package org.example.springbootadventure.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.springbootadventure.model.Order;

@Data
public class UpdateOrderStatusRequestDto {
    @NotBlank
    private Order.Status status;
}
