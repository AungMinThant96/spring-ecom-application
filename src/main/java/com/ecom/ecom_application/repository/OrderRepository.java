package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
