package com.nisal.iceflame.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartItemException extends RuntimeException {

  private final HttpStatus status;

  public CartItemException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

}
