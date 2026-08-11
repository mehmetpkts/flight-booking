package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.repository.AirlineRepository;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
  private final AirlineRepository airlineRepository;

  public AirlineController(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  // buraya crud işlemleri için kodlar gelecek

  @GetMapping
  public List<Airline> getAllAirlines() {
    return airlineRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Airline> getAirlineById(@PathVariable Long id) {
    var airline = airlineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Airline not found with id " + id));
    return ResponseEntity.ok(airline);
  }

  record CreateAirlinePayload(
      @NotEmpty(message = "Airline name must not be empty") String name,
      @NotEmpty(message = "Airline code must not be empty") String iataCode,
      @NotEmpty(message = "Airline country must not be empty") String country) {
  };

  @PostMapping
  public ResponseEntity<Airline> createAirline(@Valid @RequestBody CreateAirlinePayload payload) {
    var airline = new Airline();
    airline.setName(payload.name());
    airline.setCountry(payload.country());
    airline.setIataCode(payload.iataCode());
    var savedAirline = airlineRepository.save(airline);
    return ResponseEntity.ok(savedAirline);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airline> updateAirline(@PathVariable Long id,
      @Valid @RequestBody CreateAirlinePayload payload) {
    var airline = airlineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Airline not found with id " + id));
    airline.setName(payload.name());
    airline.setIataCode(payload.iataCode());
    airline.setCountry(payload.country());
    var updatedAirline = airlineRepository.save(airline);
    return ResponseEntity.ok(updatedAirline);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
    var airline = airlineRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Airline not found with id " + id));
    airlineRepository.delete(airline);
    return ResponseEntity.noContent().build();
  }
}