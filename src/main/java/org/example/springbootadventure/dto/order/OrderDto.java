package org.example.springbootadventure.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import org.example.springbootadventure.model.Order;
import org.example.springbootadventure.model.OrderItem;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}
