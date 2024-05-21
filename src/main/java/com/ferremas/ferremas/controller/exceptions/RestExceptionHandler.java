package com.ferremas.ferremas.controller.exceptions;

import com.ferremas.ferremas.dto.ErrorListResponse;
import com.ferremas.ferremas.dto.ErrorResponse;
import com.ferremas.ferremas.exception.RequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException ex) {
        throw new Error(ex);
        //ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        //return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> BadCredentialsExceptionHandler(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Las credenciales no son correctas.");
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<Object> JwtExceptionHandler(JwtException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(value = PropertyValueException.class)
    public ResponseEntity<Object> properyValueExceptionHandler(PropertyValueException ex) {
        String property = ex.getPropertyName();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "El campo " + property + " no puede estar vacío.");
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Object> requestExceptionHandler(RequestException ex) {
        if (ex.getErrors().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), ex.getMessage());
            return buildResponseEntity(errorResponse);
        }

        ErrorListResponse errorResponse = new ErrorListResponse(ex.getStatus(), ex.getMessage(), ex.getErrors());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
    private ResponseEntity<Object> buildResponseEntity(ErrorListResponse errorListResponse) {
        return new ResponseEntity<>(errorListResponse, errorListResponse.getStatus());
    }
}
