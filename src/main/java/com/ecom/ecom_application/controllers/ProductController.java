package com.ecom.ecom_application.controllers;

import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest,
                                                         @PathVariable Long id){
        return productService.updateProduct(productRequest, id)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
