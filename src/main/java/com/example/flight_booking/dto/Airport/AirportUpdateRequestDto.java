package com.example.flight_booking.dto.Airport;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirportUpdateRequestDto {
    @NotBlank(message = "Airport name must not be blank")
    private String name;
    @NotBlank(message = "Airport city must not be blank")
    private String city;
    @NotBlank(message = "Airport country must not be blank")
    private String country;
    @NotBlank(message = "Airport IATA code must not be blank")
    private String iataCode;
}
