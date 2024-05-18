package com.example.api.enums;



public enum PostType {

    EDITAL("Edital"), NOTICIA("Noticia"), DIVULGACAO("Divulgacao");

    private String value;


  private PostType(String value) {
    this.value = value;
  }


  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;

//    return "PostType{" +
//            "value='" + value + '\'' +
//            '}';
  }
}
