package com.ecom.ecom_application.controllers;


import com.ecom.ecom_application.dto.CartItemRequest;
import com.ecom.ecom_application.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                          @RequestBody CartItemRequest cartItemRequest){
        boolean created = cartService.addToCart(userId, cartItemRequest);
        if (created){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found.");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean deleted = cartService.deleteCartItem(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
