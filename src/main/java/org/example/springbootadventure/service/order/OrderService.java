package org.example.springbootadventure.service.order;

import org.example.springbootadventure.dto.order.CreateOrderRequestDto;
import org.example.springbootadventure.dto.order.OrderDto;
import org.example.springbootadventure.dto.order.OrderItemDto;
import org.example.springbootadventure.dto.order.UpdateOrderStatusRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto);

    Page<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    Page<OrderItemDto> getOrderItems(Long orderId,Long userId,Pageable pageable);

    OrderItemDto getOrderItem(Long userId,Long orderId,Long itemId);

}
