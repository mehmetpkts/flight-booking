package com.example.flight_booking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateResourceException extends RuntimeException {

  private final HttpStatus status;
  private final ErrorCode errorCode;

  public DuplicateResourceException(String message) {
    this(HttpStatus.CONFLICT, ErrorCode.DUPLICATE_RESOURCE, message);
  }

  public DuplicateResourceException(HttpStatus status, ErrorCode errorCode, String message) {
    super(message);
    this.status = status;
    this.errorCode = errorCode;
  }
}
