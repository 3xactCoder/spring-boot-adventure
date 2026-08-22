package org.example.springbootadventure.service.order;

import org.example.springbootadventure.dto.order.CreateOrderRequestDto;
import org.example.springbootadventure.dto.order.OrderDto;
import org.example.springbootadventure.dto.order.OrderItemDto;
import org.example.springbootadventure.dto.order.UpdateOrderStatusRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto);

    public Page<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto);

    public Page<OrderItemDto> getOrderItems(Long orderId,Long userId,Pageable pageable);

    public OrderItemDto getOrderItem(Long userId,Long orderId,Long itemId);

}
