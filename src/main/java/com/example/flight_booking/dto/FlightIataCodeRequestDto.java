package com.example.flight_booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FlightIataCodeRequestDto {
    @NotBlank(message = "Flights iata code must not be null")
    private String iataCode;
}
