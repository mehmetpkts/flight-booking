package com.example.flight_booking.dto;

import com.example.flight_booking.enums.FlightStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightFilterRequestDto {
  @NotNull(message = "Departure city must not be null")
  private String departureCity;
  @NotNull(message = "Arrival city must not be null")
  private String arrivalCity;
  @NotNull(message = "Flight status must not be null")
  private FlightStatus flightStatus;
}