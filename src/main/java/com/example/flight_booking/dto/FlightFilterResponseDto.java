package com.example.flight_booking.dto;

import lombok.Data;

@Data
public class FlightFilterResponseDto {
    private Long flightId;
    private String flightNumber;

    private String departureAirportName;
    private String departureAirportCity;

    private String arrivalAirportName;
    private String arrivalAirportCity;

    private String airlineName;
    private String airlineCountry;
}
