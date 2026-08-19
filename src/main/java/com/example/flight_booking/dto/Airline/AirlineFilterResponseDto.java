package com.example.flight_booking.dto.Airline;

import lombok.Data;

@Data
public class AirlineFilterResponseDto {

    private String name;
    private String iataCode;
    private String country;
}
