package com.ecom.ecom_application.services;

import com.ecom.ecom_application.dto.OrderItemDto;
import com.ecom.ecom_application.dto.OrderResponse;
import com.ecom.ecom_application.models.*;
import com.ecom.ecom_application.repository.CartRepository;
import com.ecom.ecom_application.repository.OrderRepository;
import com.ecom.ecom_application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        // Check user id
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()){
            return Optional.empty();
        }
        // Check cart item
        List<CartItem> cartItem = cartService.fetchUserCart(userId);
        if (cartItem.isEmpty()){
            return Optional.empty();
        }
        // Create Order
        User user = userOpt.get();
        Orders order = new Orders();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setItems(cartItem.stream()
                .map(item -> {
                    return new OrderItem(
                            null,
                            item.getProduct(),
                            item.getQuantity(),
                            item.getPrice(),
                            order
                    );
                }).toList());
        order.setTotalAmount(cartItem.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO,
                BigDecimal::add));
        Orders newOrder = orderRepository.save(order);

        // Clear cart item
        cartRepository.deleteByUser(user);


        return Optional.of(mapToOrderResponse(newOrder));
    }

    public OrderResponse mapToOrderResponse(Orders order){
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream().map(item -> {
                    return new OrderItemDto(
                            item.getId(),
                            item.getProduct(),
                            item.getQuantity(),
                            item.getPrice(),
                            BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice())
                    );
                }).toList(),
                order.getCreatedAt()
        );
    }
}