package com.example.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull(message = "O campo 'username' não pode ser nulo")
                                @NotEmpty(message = "O campo 'username' não pode ser vazio") String username,
                                @NotNull(message = "O campo 'password' não pode ser nulo")
                                @NotEmpty(message = "O campo 'password' não pode ser vazio") String password) {

}
