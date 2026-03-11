package com.nisal.iceflame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserException.class)
  public ResponseEntity<String> handleUserException(UserException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(CategoryException.class)
  public ResponseEntity<String> handleCategoryException(CategoryException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<String> handleProductException(ProductException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(CartException.class)
  public ResponseEntity<String> handleCartException(CartException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(CartItemException.class)
  public ResponseEntity<String> handleCartItemException(CartItemException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(WishlistException.class)
  public ResponseEntity<String> handleWishlistException(WishlistException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(AddressException.class)
  public ResponseEntity<String> handleAddressException(AddressException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }

  // Optional: handle other exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }
}
