package com.example.api.dto.mapper;


import com.example.api.dto.RequestUserDTO;
import com.example.api.enums.PostType;
import com.example.api.model.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RequestUserMapper {

  public RequestUserDTO toDTO(Users user) {
    if (user == null) {
      return null;
    }

    return new RequestUserDTO(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(), user.getFirst_name(),
            user.getLast_name(), user.getRole());
  }

  public Users toEntity(RequestUserDTO requestUserDTO) {

    if (requestUserDTO == null) {
      return null;
    }

    Users user = new Users();
    if (requestUserDTO.id() != null) {
      user.setId(requestUserDTO.id());
    }
    user.setEmail(requestUserDTO.email());
    user.setUsername(requestUserDTO.username());
    user.setPassword(requestUserDTO.password());
    user.setFirst_name(requestUserDTO.first_name());
    user.setLast_name(requestUserDTO.last_name());
    user.setRole(requestUserDTO.role());

    return user;
  }

  public PostType convertPostTypeValue(String value) {
    if (value == null) {
      return null;
    }
    return switch (value) {
      case "Noticia" -> PostType.NOTICIA;
      case "Edital" -> PostType.EDITAL;
      case "Divulgacao" -> PostType.DIVULGACAO;
      default -> throw new IllegalArgumentException("Tipo de postagem inválida: " + value);
    };
  }

}
