package com.example.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String username;
    private String itemName;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private String status;
}
