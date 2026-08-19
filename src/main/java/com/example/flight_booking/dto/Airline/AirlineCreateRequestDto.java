package com.example.flight_booking.dto.Airline;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirlineCreateRequestDto {
            @NotBlank(message = "Airline name must not be blank")
            private String name;
            @NotBlank(message = "Airline code must not be blank")
            private String iataCode;
            @NotBlank(message = "Airline country must not be blank")
            private String country;
}
