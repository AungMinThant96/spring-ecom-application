package com.ecom.ecom_application.dto;

import com.ecom.ecom_application.models.Orders;
import com.ecom.ecom_application.models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
