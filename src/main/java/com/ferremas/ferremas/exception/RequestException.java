package com.ferremas.ferremas.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class RequestException extends RuntimeException {
    HttpStatus status;
    HashMap<String, String> errors;

    public RequestException(String message, HttpStatus status) {
        super(message);
        this.errors = new HashMap<>();
        this.status = status;
    }
    public RequestException(String message, HttpStatus status, HashMap<String, String> errors) {
        super(message);
        this.errors = errors;
        this.status = status;
    }
}
