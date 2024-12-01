package com.jwt.springboot.controller;

import com.jwt.springboot.dao.handle.ResponseErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseErrorDTO> handleNullPointerException(NullPointerException ex) {
        logger.error("Error null pointer: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseErrorDTO.builder()
                .message("Null Pointer")
                .error(String.valueOf(HttpStatus.BAD_REQUEST))
                .details(ex.getMessage())
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("Error Invalid request body: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseErrorDTO.builder()
                .message("Invalid request body. Please check your JSON structure.")
                .error(String.valueOf(HttpStatus.BAD_REQUEST))
                .details(ex.getLocalizedMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal Argument: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseErrorDTO.builder()
                .message("Illegal Argument")
                .error(String.valueOf(HttpStatus.BAD_REQUEST))
                .details(ex.getLocalizedMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDTO> handleGeneralException(Exception ex) {
        logger.error("Unhandled Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseErrorDTO.builder()
                .message("An unexpected error occurred")
                .error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .details(ex.getLocalizedMessage())
                .build());
    }

}
