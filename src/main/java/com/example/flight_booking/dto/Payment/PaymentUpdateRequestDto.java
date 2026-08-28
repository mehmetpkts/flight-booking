package com.example.flight_booking.dto.Payment;

import com.example.flight_booking.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentUpdateRequestDto {
  @NotNull(message = "Booking ID must not be null")
  private Long bookingId;
  @NotNull(message = "Amount must not be null")
  @PositiveOrZero(message = "Amount must be zero or positive")
  private BigDecimal amount;
  @NotBlank(message = "Payment method must not be blank")
  private String paymentMethod;
  @NotNull(message = "Payment date must not be null")
  private LocalDateTime paymentDate;
  @NotNull(message = "Status must not be null")
  private PaymentStatus status;
}
