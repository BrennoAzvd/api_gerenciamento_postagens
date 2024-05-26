package com.example.api.dto;

import com.example.api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public record RequestUserDTO (
        @JsonProperty("_id") UUID id,
        @Email(message = "The email format is invalid")
        String email,
        @NotBlank(message = "Username cannot be blank")
        String username,
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,
        @NotBlank(message = "First name cannot be blank")
        String first_name,
        @NotBlank(message = "Last name cannot be blank")
        String last_name,
        @NotNull(message = "role cannot be null")
        UserRole role


) {
}
