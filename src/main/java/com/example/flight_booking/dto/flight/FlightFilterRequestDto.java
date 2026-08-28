package com.example.flight_booking.dto.flight;

import com.example.flight_booking.enums.FlightStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FlightFilterRequestDto {
  @NotBlank(message = "Departure city must not be blank")
  @Pattern(regexp = "^[A-Z]{3}$", message = "Departure city must be a valid IATA code (3 uppercase letters)")
  private String departureCity;
  @NotBlank(message = "Arrival city must not be blank")
  @Pattern(regexp = "^[A-Z]{3}$", message = "Arrival city must be a valid IATA code (3 uppercase letters)")
  private String arrivalCity;
  @NotNull(message = "Flight status must not be null")
  private FlightStatus flightStatus;
}