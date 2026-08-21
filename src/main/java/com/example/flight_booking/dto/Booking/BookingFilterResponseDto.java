package com.example.flight_booking.dto.Booking;

import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingFilterResponseDto {

    private Passenger passenger;
    private Flight flight;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private String pnr;
    private Boolean cancellationPenaltyApplied;
    private BigDecimal cancellationPenaltyAmount;
}
