package com.example.api.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

  @Override
  public String convertToDatabaseColumn(UserRole role) {
    if (role == null) {
      return null;
    }
    return role.getRole();
  }

  @Override
  public UserRole convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(UserRole.values())
            .filter(c -> c.getRole().equals(value))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
  }
}
