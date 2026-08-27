package com.example.flight_booking.dto.Payment;

import com.example.flight_booking.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentFilterResponseDto {
  private Long paymentId;
  private Long bookingId;
  private BigDecimal amount;
  private String paymentMethod;
  private LocalDateTime paymentDate;
  private PaymentStatus status;
}
