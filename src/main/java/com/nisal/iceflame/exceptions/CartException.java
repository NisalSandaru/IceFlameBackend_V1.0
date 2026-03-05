package com.nisal.iceflame.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartException extends RuntimeException {

    private final HttpStatus status;

    public CartException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
