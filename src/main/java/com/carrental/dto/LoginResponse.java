package com.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data @AllArgsConstructor
public class LoginResponse {
    String token;
}