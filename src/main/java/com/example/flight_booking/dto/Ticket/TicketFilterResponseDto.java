package com.example.flight_booking.dto.Ticket;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TicketFilterResponseDto {
  private Long ticketId;
  private Long bookingId;
  private String ticketNumber;
  private LocalDateTime issueDate;
  private BigDecimal price;
}
