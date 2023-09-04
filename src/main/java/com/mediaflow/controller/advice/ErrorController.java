package com.mediaflow.controller.advice;

import com.mediaflow.dto.ErrorResponse;
import com.mediaflow.exception.PhotoException;
import com.mediaflow.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({UsernameNotFoundException.class, PhotoException.class})
    public ResponseEntity<ErrorResponse> usernameNotFound(RuntimeException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(NOT_FOUND, e.getMessage(), request.getServletPath());
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(BAD_REQUEST, e.getMessage(), request.getServletPath());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValidArgument(MethodArgumentNotValidException e, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(new Date())
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .messages(e.getFieldErrors().stream()
                        .collect(Collectors.toMap(FieldError::getField, fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Error"))))
                .path(request.getServletPath())
                .build();

        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> notValidArgument(ConstraintViolationException e, HttpServletRequest request) {
        ErrorResponse response = buildErrorResponse(BAD_REQUEST, e.getMessage(), request.getServletPath());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }

}
