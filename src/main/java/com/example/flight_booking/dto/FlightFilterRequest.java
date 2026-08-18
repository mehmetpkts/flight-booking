package com.example.flight_booking.dto;

import com.example.flight_booking.enums.FlightStatus;

public record FlightFilterRequest(
    String departureCity,
    String arrivalCity,
    FlightStatus flightStatus) {
}