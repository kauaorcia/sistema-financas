package com.kaua.demo.dto;

public record UserResponse(
        Long id,
        String nome,
        String email
) {}
