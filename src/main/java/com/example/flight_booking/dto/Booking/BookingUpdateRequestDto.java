package com.example.flight_booking.dto.Booking;

import com.example.flight_booking.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingUpdateRequestDto {

  @NotNull(message = "Booking status must not be null")
  private BookingStatus status;
}
