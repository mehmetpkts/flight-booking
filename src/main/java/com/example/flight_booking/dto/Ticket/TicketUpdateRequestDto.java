package com.example.flight_booking.dto.Ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketUpdateRequestDto {
  @NotNull(message = "Price must not be null")
  @PositiveOrZero(message = "Price must be zero or positive")
  private BigDecimal price;
}
