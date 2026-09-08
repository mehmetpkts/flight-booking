package com.example.flight_booking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private final HttpStatus status;
  private final ErrorCode errorCode;

  public ResourceNotFoundException(String resourceName, Object identifier) {
    this(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND,
        resourceName + " not found with id " + identifier);
  }

  public ResourceNotFoundException(HttpStatus status, String message) {
    this(status, ErrorCode.RESOURCE_NOT_FOUND, message);
  }

  public ResourceNotFoundException(HttpStatus status, ErrorCode errorCode, String message) {
    super(message);
    this.status = status;
    this.errorCode = errorCode;
  }
}
