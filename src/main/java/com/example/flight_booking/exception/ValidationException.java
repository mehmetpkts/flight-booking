package com.example.flight_booking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {

  private final HttpStatus status;
  private final ErrorCode errorCode;

  public ValidationException(String message) {
    this(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, message);
  }

  public ValidationException(HttpStatus status, ErrorCode errorCode, String message) {
    super(message);
    this.status = status;
    this.errorCode = errorCode;
  }
}
