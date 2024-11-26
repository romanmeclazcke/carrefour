package org.example.carrefour.Utils.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.example.carrefour.Utils.Dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    private static final String GENERIC_ERROR = "GENERIC_ERROR";
    private static final String BAD_REQUEST_ERROR = "BAD_REQUEST";
    private static final String ENTITY_NOT_FOUND_ERROR = "ENTITY_NOT_FOUND";
    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDto> runtimeExceptionHandler(RuntimeException e) {
        return buildErrorResponse(
                "An unexpected error occurred. Please contact support.",
                GENERIC_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDto> badRequestExceptionHandler(BadRequestException e) {
        return buildErrorResponse(
                e.getMessage(),
                BAD_REQUEST_ERROR,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundExceptionHandler(EntityNotFoundException e) {
        return buildErrorResponse(
                "The requested entity does not exist.",
                ENTITY_NOT_FOUND_ERROR,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorDto> internalServerExceptionHandler(HttpServerErrorException.InternalServerError e) {
        return buildErrorResponse(
                "Internal server error occurred. Please contact support.",
                INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
                );
    }

    private ResponseEntity<ErrorDto> buildErrorResponse(String message, String errorCode, HttpStatus status) {
        ErrorDto errorDto = ErrorDto.builder()
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDto, status);
    }
}