package com.example.api.service;

import com.example.api.dto.RegisterDTO;
import com.example.api.dto.RequestUserDTO;
import com.example.api.dto.mapper.RegisterMapper;
import com.example.api.dto.mapper.RequestUserMapper;
import com.example.api.exception.RecordNotFoundException;
import com.example.api.model.Users;
import com.example.api.repository.UserRepository;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RequestUserMapper requestUserMapper;
  private final RegisterMapper registerMapper;

  public UserService(UserRepository userRepository, RequestUserMapper requestUserMapper) {
    this.userRepository = userRepository;
    this.requestUserMapper = requestUserMapper;
    this.registerMapper = new RegisterMapper();
  }

  public List<RequestUserDTO> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(requestUserMapper::toDTO)
            .collect(Collectors.toList());
  }

  public RequestUserDTO getUserById(@PathVariable @NotNull UUID id) {
    return userRepository.findById(id)
            .map(requestUserMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
  }

  public RegisterDTO createUser(@Valid @NotNull RegisterDTO user) {

    return registerMapper.toDTO(userRepository.save(registerMapper.toEntity(user)));
  }

  public RegisterDTO updateUser(@NotNull UUID id, @Valid @NotNull RegisterDTO data) {
    return userRepository.findById(id)
            .map(recordFound -> {
              recordFound.setEmail(data.email());
              recordFound.setUsername(data.username());
              recordFound.setPassword(data.password());
              recordFound.setFirst_name(data.first_name());
              recordFound.setLast_name(data.last_name());
              recordFound.setRole(data.role());
              return registerMapper.toDTO(userRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
  }

  public void deleteUser(@PathVariable @NotNull UUID id) {
    userRepository.delete(userRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
  }


}
