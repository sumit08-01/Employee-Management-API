package com.api.ems.exception;

import com.api.ems.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GolabExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseDTO.prepare(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
