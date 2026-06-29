package com.ecom.ecom_application.services;


import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.models.CartItem;
import com.ecom.ecom_application.models.Product;
import com.ecom.ecom_application.models.User;
import com.ecom.ecom_application.repository.CartRepository;
import com.ecom.ecom_application.repository.ProductRepository;
import com.ecom.ecom_application.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if (productOpt.isEmpty()){
            return false;
        }
        Product product = productOpt.get();
        if (product.getStockQuantity() <= 0 || product.getStockQuantity() < cartItemRequest.getQuantity()){
            return false;
        }
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();
        CartItem cartItem = cartRepository.findByUserAndProduct(user,product);
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartRepository.save(cartItem);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            cartRepository.save(newCartItem);
        }
        return true;

    }
}
