package com.example.flight_booking.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
      ResourceNotFoundException ex,
      HttpServletRequest request) {
    logger.warn("Resource not found: {}", ex.getMessage());
    return buildResponse(ex.getStatus(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(BusinessRuleException.class)
  public ResponseEntity<ApiErrorResponse> handleBusinessRuleException(
      BusinessRuleException ex,
      HttpServletRequest request) {
    logger.warn("Business rule violation: {}", ex.getMessage());
    return buildResponse(ex.getStatus(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(
      DuplicateResourceException ex,
      HttpServletRequest request) {
    logger.warn("Duplicate resource: {}", ex.getMessage());
    return buildResponse(ex.getStatus(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationException(
      ValidationException ex,
      HttpServletRequest request) {
    logger.warn("Validation error: {}", ex.getMessage());
    return buildResponse(ex.getStatus(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
      ResponseStatusException ex,
      HttpServletRequest request) {
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    String message = ex.getReason() != null ? ex.getReason() : "Request could not be processed.";
    logger.warn("Request error. Status: {}, Message: {}", status, message);
    return buildResponse(status, message, request.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));

    logger.warn("Validation failed: {}", message);
    return buildResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
      DataIntegrityViolationException ex,
      HttpServletRequest request) {
    logger.error("Database integrity violation.", ex);
    return buildResponse(HttpStatus.CONFLICT,
        "Database constraint violation detected.",
        request.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
      Exception ex,
      HttpServletRequest request) {
    logger.error("Unexpected server error occurred.", ex);
    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Unexpected server error occurred.",
        request.getRequestURI());
  }

  private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, String path) {
    return ResponseEntity.status(status)
        .body(ApiErrorResponse.of(status, message, path));
  }
}
