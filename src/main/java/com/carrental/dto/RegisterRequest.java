package com.carrental.dto;

import com.carrental.model.enums.UserRole;

import lombok.Data;


@Data
public class RegisterRequest {
    String name;
    String lastName;
    String email;
    String password;
    UserRole role;
}
