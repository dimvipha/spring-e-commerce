package com.vipha.ecommerce.exception;

import jakarta.servlet.annotation.HandlesTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalAppException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiErrorResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ApiErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .isSuccess(false)
                .message("Data submission has validation failed ")
                .timestamp(Instant.now())
                .errorDetail(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode())
                .body(
                        ApiErrorResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .isSuccess(false)
                                .message("Data submission has validation failed ")
                                .timestamp(Instant.now())
                                .errorDetail(e.getReason())
                                .build()
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiErrorResponse<?> handleJsonResponse(HttpMessageNotReadableException e){
        return ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .isSuccess(false)
                .message("Data submission has validation failed ")
                .timestamp(Instant.now())
                .errorDetail(e.getLocalizedMessage())
                .build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse<?> handleValidationException(MethodArgumentNotValidException e) {
        List<Map<String, Object>> errorList = new ArrayList<>();

        e.getFieldErrors().forEach(fieldError -> {
            Map<String, Object> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("reason",  fieldError.getDefaultMessage());
            errorList.add(error);
        });

        return ApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .isSuccess(false)
                .message("Data submission has validation failed ")
                .timestamp(Instant.now())
                .errorDetail(errorList)
                .build();

    }
}
