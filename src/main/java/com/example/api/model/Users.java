package com.example.api.model;

import com.example.api.dto.RequestUserDTO;
import com.example.api.enums.PostTypeConverter;
import com.example.api.enums.UserRole;
import com.example.api.enums.UserRoleConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import java.time.LocalDate;
import java.util.UUID;

@Table(name ="users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users implements UserDetails {

  @Id
  @UuidGenerator
  @JsonProperty("_id")
  private UUID id;

  @NotBlank
  @NotNull
  private String username;

  @NotBlank
  @NotNull
  private String password;

  @NotBlank
  @NotNull
  private String email;

  @NotBlank
  @NotNull
  private String first_name;

  @NotBlank
  @NotNull
  private String last_name;

  @NotNull
  @Convert(converter = UserRoleConverter.class)
  private UserRole role;

  public Users(RequestUserDTO requestUserDTO) {
    this.password = requestUserDTO.password();
    this.email = requestUserDTO.email();
    this.first_name = requestUserDTO.first_name();
    this.last_name = requestUserDTO.last_name();
    this.role = requestUserDTO.role();
  }

  public Users(String username, String password,String email,
              String firstName, String lastName, UserRole role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.first_name = firstName;
    this.last_name = lastName;
    this.role = role;
  }

  public Users(String username, String password, UserRole role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
