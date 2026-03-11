package com.nisal.iceflame.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AddressException extends RuntimeException {

    private final HttpStatus status;

    public AddressException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}