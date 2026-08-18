package com.example.flight_booking.dto.flight;

import com.example.flight_booking.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class FlightCreateRequestDto {
    @NotBlank(message = "Flight number must not be blank")
    private String flightNumber;
    @NotNull(message = "Departure airport ID must not be null")
    private Long departureAirportId;
    @NotNull(message = "Arrival airport ID must not be null")
    private Long arrivalAirportId;
    @NotNull(message = "Aircraft ID must not be null")
    private Long aircraftId;
    @NotNull(message = "Airline ID must not be null")
    private Long airlineId;
    @NotNull(message = "Departure time must not be null")
    private LocalDateTime departureTime;
    @NotNull(message = "Arrival time must not be null")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Flight status must not be null")
    private FlightStatus status;

    public static record FlightFilterRequest(
        String departureCity,
        String arrivalCity,
        FlightStatus flightStatus) {
    }
}
