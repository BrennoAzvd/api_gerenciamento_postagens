package com.example.api.exception;

import java.util.UUID;

public class RecordNotFoundException extends RuntimeException{

  private static final long serialVersionUID = 1L;

  public RecordNotFoundException(UUID id) {
    super ("Registro não encontrado com ID: "+ id);
  }

}
