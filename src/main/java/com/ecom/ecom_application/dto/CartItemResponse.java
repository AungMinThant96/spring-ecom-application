package com.ecom.ecom_application.dto;

import com.ecom.ecom_application.models.Product;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItemResponse {
    private ProductResponse productResponse;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
