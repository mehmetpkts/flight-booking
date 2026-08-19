package com.example.flight_booking.dto.Airport;

import lombok.Data;

@Data
public class AirportFilterResponseDto {
    private String name;
    private String city;
    private String country;
    private String iataCode;
}
