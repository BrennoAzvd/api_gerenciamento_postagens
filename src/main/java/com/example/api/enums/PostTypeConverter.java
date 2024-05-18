package com.example.api.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType, String> {

  @Override
  public String convertToDatabaseColumn(PostType postType) {
    if (postType == null) {
      return null;
    }
    return postType.getValue();
  }

  @Override
  public PostType convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(PostType.values())
            .filter(c -> c.getValue().equals(value))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
  }

}
