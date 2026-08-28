package com.example.flight_booking.dto.Ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TicketUpdateRequestDto {
  @NotNull(message = "Booking ID must not be null")
  private Long bookingId;
  @NotBlank(message = "Ticket number must not be blank")
  private String ticketNumber;
  @NotNull(message = "Issue date must not be null")
  private LocalDateTime issueDate;
  @NotNull(message = "Price must not be null")
  @PositiveOrZero(message = "Price must be zero or positive")
  private BigDecimal price;
}
