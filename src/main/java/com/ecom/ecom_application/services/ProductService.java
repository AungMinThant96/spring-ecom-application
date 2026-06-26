package com.ecom.ecom_application.services;

import com.ecom.ecom_application.dto.ProductRequest;
import com.ecom.ecom_application.dto.ProductResponse;
import com.ecom.ecom_application.models.Product;
import com.ecom.ecom_application.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        return mapToProductResponse(productRepository.save(product));
    }

    public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, productRequest);
            return mapToProductResponse(productRepository.save(existingProduct));
        });
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
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
