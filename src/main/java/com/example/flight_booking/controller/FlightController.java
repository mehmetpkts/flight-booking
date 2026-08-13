package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.FlightStatus;
import com.example.flight_booking.service.FlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public List<Flight> getAllFlights() {
    return flightService.getAllFlights();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
    return ResponseEntity.ok(flightService.getFlightById(id));
  }

  record CreateFlightPayload(
      @NotBlank(message = "Flight number must not be blank") String flightNumber,
      @NotNull(message = "Departure airport ID must not be null") Long departureAirportId,
      @NotNull(message = "Arrival airport ID must not be null") Long arrivalAirportId,
      @NotNull(message = "Aircraft ID must not be null") Long aircraftId,
      @NotNull(message = "Airline ID must not be null") Long airlineId,
      @NotNull(message = "Departure time must not be null") LocalDateTime departureTime,
      @NotNull(message = "Arrival time must not be null") LocalDateTime arrivalTime,
      @NotNull(message = "Flight status must not be null") FlightStatus status) {
  }

  @PostMapping
  public ResponseEntity<Flight> createFlight(@Valid @RequestBody CreateFlightPayload payload) {
    Flight savedFlight = flightService.createFlight(
        payload.flightNumber(),
        payload.departureAirportId(),
        payload.arrivalAirportId(),
        payload.aircraftId(),
        payload.airlineId(),
        payload.departureTime(),
        payload.arrivalTime(),
        payload.status());
    return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Flight> updateFlight(@PathVariable Long id,
      @Valid @RequestBody CreateFlightPayload payload) {
    Flight updatedFlight = flightService.updateFlight(
        id,
        payload.flightNumber(),
        payload.departureAirportId(),
        payload.arrivalAirportId(),
        payload.aircraftId(),
        payload.airlineId(),
        payload.departureTime(),
        payload.arrivalTime(),
        payload.status());
    return ResponseEntity.ok(updatedFlight);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
    flightService.deleteFlight(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public List<Flight> getFlightsByIataCode(@RequestParam String iataCode) {
    return flightService.getFlightsByIataCode(iataCode);
  }
}
