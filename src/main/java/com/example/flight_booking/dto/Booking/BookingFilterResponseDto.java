package com.example.flight_booking.dto.Booking;

import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.flight.FlightFilterResponseDto;
import com.example.flight_booking.enums.BookingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingFilterResponseDto {

    private PassengerFilterResponseDto passenger;
    private FlightFilterResponseDto flight;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private String pnr;
    private Boolean cancellationPenaltyApplied;
    private BigDecimal cancellationPenaltyAmount;
}
