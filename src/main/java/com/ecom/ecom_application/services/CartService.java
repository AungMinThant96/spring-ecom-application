package com.ecom.ecom_application.services;


import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.dto.CartItemResponse;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.models.CartItem;
import com.ecom.ecom_application.models.Product;
import com.ecom.ecom_application.models.User;
import com.ecom.ecom_application.repository.CartRepository;
import com.ecom.ecom_application.repository.ProductRepository;
import com.ecom.ecom_application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

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

    public boolean deleteCartItem(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (productOpt.isPresent() && userOpt.isPresent()){
            cartRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }

    public List<CartItem> fetchUserCart(String userId) {
//        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
//        if (userOpt.isEmpty()){
//            return Optional.empty();
//        }
//        return Optional.of(
//                cartRepository.findByUser(userOpt.get())
//                        .stream()
//                        .map(this::mapToCartItemResponse)
//                        .collect(Collectors.toList())
//        );
        return userRepository.findById(Long.valueOf(userId))
                .map(cartRepository::findByUser).orElseGet(List::of);
    }

    public CartItemResponse mapToCartItemResponse(CartItem cartItem){
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setProductResponse(mapToProductResponse(cartItem.getProduct()));
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setPrice(cartItem.getPrice());
        cartItemResponse.setCreatedAt(cartItem.getCreatedAt());
        cartItemResponse.setUpdatedAt(cartItem.getUpdatedAt());
        return cartItemResponse;
    }

    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());

        return productResponse;
    }


}
