package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.service.AirlineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
  private final AirlineService airlineService;

  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @GetMapping
  public List<Airline> getAllAirlines() {
    return airlineService.getAllAirlines();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Airline> getAirlineById(@PathVariable Long id) {
    return ResponseEntity.ok(airlineService.getAirlineById(id));
  }

  record CreateAirlinePayload(
      @NotEmpty(message = "Airline name must not be empty") String name,
      @NotEmpty(message = "Airline code must not be empty") String iataCode,
      @NotEmpty(message = "Airline country must not be empty") String country) {
  }

  @PostMapping
  public ResponseEntity<Airline> createAirline(@Valid @RequestBody CreateAirlinePayload payload) {
    Airline savedAirline = airlineService.createAirline(
        payload.name(),
        payload.iataCode(),
        payload.country());
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirline);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airline> updateAirline(@PathVariable Long id,
      @Valid @RequestBody CreateAirlinePayload payload) {
    Airline updatedAirline = airlineService.updateAirline(
        id,
        payload.name(),
        payload.iataCode(),
        payload.country());
    return ResponseEntity.ok(updatedAirline);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
    airlineService.deleteAirline(id);
    return ResponseEntity.noContent().build();
  }
}