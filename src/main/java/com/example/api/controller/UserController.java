package com.example.api.controller;

import com.example.api.dto.RegisterDTO;
import com.example.api.dto.RequestUserDTO;
import com.example.api.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping
  public List<RequestUserDTO> getAllUsers() {
    return userService.getAllUsers();
  }


  @GetMapping("/{id}")
  public RequestUserDTO getUserById(@PathVariable UUID id) {
    return userService.getUserById(id);
  }


  @PostMapping("/auth/register")
  @ResponseStatus(code = HttpStatus.CREATED)
  public RegisterDTO createUser(@RequestBody RegisterDTO data) {
    return userService.createUser(data);
  }


  @PutMapping("/{id}")
  public RegisterDTO updateUser(@PathVariable @NotNull UUID id,
                                   @RequestBody @Valid @NotNull RegisterDTO data) {
    return userService.updateUser(id, data);
  }


   @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable @NotNull UUID id) {
    userService.deleteUser(id);
  }

}
