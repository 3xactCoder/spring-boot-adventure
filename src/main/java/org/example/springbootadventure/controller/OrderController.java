package org.example.springbootadventure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.order.CreateOrderRequestDto;
import org.example.springbootadventure.dto.order.OrderDto;
import org.example.springbootadventure.dto.order.OrderItemDto;
import org.example.springbootadventure.dto.order.UpdateOrderStatusRequestDto;
import org.example.springbootadventure.model.User;
import org.example.springbootadventure.service.order.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Place an order",
            description = "Place a new order based on current shopping cart items")
    public OrderDto placeOrder(
            Authentication authentication,
            @RequestBody @Valid CreateOrderRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user.getId(), requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Retrieve order history",
            description = "Retrieve all placed orders of the current user")
    public Page<OrderDto> getOrdersHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderHistory(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Retrieve order items",
            description = "Retrieve all items for a specific user order")
    public Page<OrderItemDto> getOrderItems(
            Authentication authentication,
            @PathVariable Long orderId,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(user.getId(), orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Retrieve a specific order item",
            description = "Retrieve details for a specific item inside an order")
    public OrderItemDto getOrderItem(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long id
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItem(user.getId(), orderId, id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status",
            description = "Update the processing status of an order")
    public OrderDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusRequestDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
