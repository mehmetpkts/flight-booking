package com.example.flight_booking.controller;

import com.example.flight_booking.service.AirportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.flight_booking.entity.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

  private final AirportService airportService;

  public AirportController(AirportService airportService) {
    this.airportService = airportService;
  }

  // crud işlemlerii

  @GetMapping()
  public List<Airport> getAllAirports() {
    return airportService.getAllAirports();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
    return ResponseEntity.ok(airportService.getAirportById(id));
  }

  record CreateAirportPayload(
      @NotBlank(message = "Airport name must not be blank") String name,
      @NotBlank(message = "Airport city must not be blank") String city,
      @NotBlank(message = "Airport country must not be blank") String country,
      @NotBlank(message = "Airport IATA code must not be blank") String iataCode) {
  };

  @PostMapping
  public ResponseEntity<Airport> createAirport(@Valid @RequestBody CreateAirportPayload payload) {
    Airport savedAirport = airportService.createAirport(
        payload.name(),
        payload.city(),
        payload.country(),
        payload.iataCode());
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirport);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airport> updateAirport(@PathVariable Long id,
      @Valid @RequestBody CreateAirportPayload payload) {
    Airport updatedAirport = airportService.updateAirport(
        id,
        payload.name(),
        payload.city(),
        payload.country(),
        payload.iataCode());
    return ResponseEntity.ok(updatedAirport);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
    airportService.deleteAirport(id);
    return ResponseEntity.noContent().build();
  }

}
