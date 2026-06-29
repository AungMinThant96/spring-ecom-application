package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.models.CartItem;
import com.ecom.ecom_application.models.Product;
import com.ecom.ecom_application.models.User;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}
