package com.example.api.controller;

import com.example.api.dto.AuthenticationDTO;
import com.example.api.dto.LoginResponseDTO;
import com.example.api.model.Users;
import com.example.api.repository.UserRepository;
import com.example.api.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController<e> {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository repository;
  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
      var auth = this.authenticationManager.authenticate(usernamePassword);

      var token = tokenService.generateToken((Users) auth.getPrincipal());
      var msg = "Sucess";
      return ResponseEntity.ok(new LoginResponseDTO(token, msg));

    } catch (AuthenticationException e) {
      // Manejar a exceção de autenticação e retornar um erro
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
              new LoginResponseDTO("", "Login fail: Authentication error"));
    }

  }

}
