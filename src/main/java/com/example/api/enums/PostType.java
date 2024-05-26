package com.example.api.enums;


import lombok.Getter;

@Getter
public enum PostType {

    EDITAL("Edital"), NOTICIA("Noticia"), DIVULGACAO("Divulgacao");

    private final String value;


  private PostType(String value) {
    this.value = value;
  }


  @Override
  public String toString() {
    return value;

  }
}
