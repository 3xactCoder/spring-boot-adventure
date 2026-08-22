package org.example.springbootadventure.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.springbootadventure.dto.order.CreateOrderRequestDto;
import org.example.springbootadventure.dto.order.OrderDto;
import org.example.springbootadventure.dto.order.OrderItemDto;
import org.example.springbootadventure.dto.order.UpdateOrderStatusRequestDto;
import org.example.springbootadventure.exceptions.EntityNotFoundException;
import org.example.springbootadventure.mapper.OrderItemMapper;
import org.example.springbootadventure.mapper.OrderMapper;
import org.example.springbootadventure.model.CartItem;
import org.example.springbootadventure.model.Order;
import org.example.springbootadventure.model.OrderItem;
import org.example.springbootadventure.model.ShoppingCart;
import org.example.springbootadventure.repository.cart.ShoppingCartRepository;
import org.example.springbootadventure.repository.order.OrderItemRepository;
import org.example.springbootadventure.repository.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart for user with id "
                        + userId)
        );
        if (shoppingCart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot place order with an empty cart");
        }

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());

        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItem -> toOrderItem(order, cartItem))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        BigDecimal total = shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId,pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + orderId)
        );
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderItemDto> getOrderItems(Long orderId, Long userId, Pageable pageable) {
        return orderItemRepository.findAllByOrderIdAndOrderUserId(orderId,userId,pageable)
                 .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findByIdAndOrderIdAndOrderUserId(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item with id: " + itemId + " in order with id: " + orderId
                ));
        return orderItemMapper.toDto(orderItem);
    }

    private OrderItem toOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }

    private Order getOrderForUser(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id: "
                        + orderId));
        return order;
    }
}
