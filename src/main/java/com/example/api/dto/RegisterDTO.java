package com.example.api.dto;

import com.example.api.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record RegisterDTO(
        @JsonProperty("_id") UUID id,
        @Email(message = "The email format is invalid")
                          String email,
                          @NotBlank(message = "Username cannot be blank")
                          String username,

                          String password,
                          @NotBlank(message = "First name cannot be blank")
                          @NotNull(message = "First name cannot be null")
                          String first_name,
                          @NotBlank(message = "Last name cannot be blank")
                          @NotNull(message = "Last name cannot be null")
                          String last_name,
                          @NotNull(message = "role cannot be null")
                          UserRole role

                          ) {
}
