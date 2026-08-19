package com.example.flight_booking.dto.Booking;

import com.example.flight_booking.enums.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingUpdateRequestDto {
    @NotNull(message = "Passenger ID must not be null")
    private Long passengerId;
    @NotNull(message = "Flight ID must not be null")
    private Long flightId;
    @NotNull(message = "Booking date must not be null")
    private LocalDateTime bookingDate;
    @NotNull(message = "Booking status must not be null")
    private BookingStatus status;
    @NotBlank(message = "PNR must not be blank")
    private String pnr;
}
