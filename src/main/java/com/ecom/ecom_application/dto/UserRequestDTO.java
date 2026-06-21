package com.ecom.ecom_application.dto;

import com.ecom.ecom_application.models.UserRole;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole userRole;
    private AddressDTO addressDto;
}
