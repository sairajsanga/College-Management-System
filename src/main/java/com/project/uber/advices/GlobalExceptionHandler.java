package com.project.uber.advices;

import com.project.uber.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.project.uber.exceptions.RuntimeConflictException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError=ApiError.
                builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return ResponseEntity
                .status(apiError.getStatus())
                .body(new ApiResponse<>(apiError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerErrorException(Exception ex) {
        ApiError apiError=ApiError.
                builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.
                status(apiError.getStatus())
                .body(new ApiResponse<>(apiError));
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(RuntimeConflictException ex) {
        ApiError apiError=ApiError.
                builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.
                status(apiError.getStatus())
                .body(new ApiResponse<>(apiError));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors=ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .toList();
        ApiError apiError=ApiError.
                builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .subErrors(errors)
                .build();
        return ResponseEntity
                .status(apiError.getStatus())
                .body(new ApiResponse<>(apiError));
    }
}
