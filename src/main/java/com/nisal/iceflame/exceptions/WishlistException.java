package com.nisal.iceflame.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WishlistException extends RuntimeException {

    private final HttpStatus status;

    public WishlistException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}