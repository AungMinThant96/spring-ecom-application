package com.ecom.ecom_application.repository;

import com.ecom.ecom_application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
