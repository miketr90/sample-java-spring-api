package com.example.userapi.service;

import com.example.userapi.dto.OrderRequest;
import com.example.userapi.dto.OrderResponse;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.model.Order;
import com.example.userapi.model.User;
import com.example.userapi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Transactional
    public OrderResponse createOrder(String username, OrderRequest orderRequest) {
        User user = userService.findUserByUsername(username);

        // Generate a unique order number
        String orderNumber = generateOrderNumber();

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUser(user);
        order.setItemName(orderRequest.getItemName());
        order.setDescription(orderRequest.getDescription());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public List<OrderResponse> getUserOrders(String username) {
        User user = userService.findUserByUsername(username);
        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long orderId, String username) {
        User user = userService.findUserByUsername(username);
        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        // Ensure the order belongs to the user
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
        
        return convertToDTO(order);
    }

    private OrderResponse convertToDTO(Order order) {
        BigDecimal totalAmount = order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getUsername(),
                order.getItemName(),
                order.getDescription(),
                order.getQuantity(),
                order.getPrice(),
                totalAmount,
                order.getOrderDate(),
                order.getStatus()
        );
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
