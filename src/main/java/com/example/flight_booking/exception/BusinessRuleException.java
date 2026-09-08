package com.example.flight_booking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessRuleException extends RuntimeException {

  private final HttpStatus status;
  private final ErrorCode errorCode;

  public BusinessRuleException(String message) {
    this(HttpStatus.BAD_REQUEST, ErrorCode.BUSINESS_RULE_VIOLATION, message);
  }

  public BusinessRuleException(HttpStatus status, String message) {
    this(status, ErrorCode.BUSINESS_RULE_VIOLATION, message);
  }

  public BusinessRuleException(HttpStatus status, ErrorCode errorCode, String message) {
    super(message);
    this.status = status;
    this.errorCode = errorCode;
  }
}
