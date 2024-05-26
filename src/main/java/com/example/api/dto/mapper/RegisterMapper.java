package com.example.api.dto.mapper;

import com.example.api.dto.RegisterDTO;
import com.example.api.model.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {

  public RegisterDTO toDTO(Users user) {
    if (user == null) {
      return null;
    }

    return new RegisterDTO(user.getId(), user.getEmail(), user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), user.getFirst_name(),
            user.getLast_name(), user.getRole());
  }

  public Users toEntity(RegisterDTO registerDTO) {

    if (registerDTO == null) {
      return null;
    }

    Users user = new Users();
    if (registerDTO.id() != null) {
      user.setId(registerDTO.id());
    }
    user.setEmail(registerDTO.email());
    user.setUsername(registerDTO.username());
    user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.password()));
    user.setFirst_name(registerDTO.first_name());
    user.setLast_name(registerDTO.last_name());
    user.setRole(registerDTO.role());

    return user;
  }

}
