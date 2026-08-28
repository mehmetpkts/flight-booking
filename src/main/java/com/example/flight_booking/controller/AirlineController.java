package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.dto.Airline.AirlineUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.service.AirlineService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
  private final AirlineService airlineService;

  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @GetMapping
  public ResponseEntity<List<AirlineFilterResponseDto>> getAllAirlines() {
    return ResponseEntity.ok(airlineService.getAllAirlines());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AirlineFilterResponseDto> getAirlineById(@PathVariable Long id) {
    return ResponseEntity.ok(airlineService.getAirlineById(id));
  }

  @PostMapping
  public ResponseEntity<Airline> createAirline(@Valid @RequestBody AirlineCreateRequestDto create) {
    Airline savedAirline = airlineService.createAirline(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirline);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airline> updateAirline(@PathVariable Long id,
      @Valid @RequestBody AirlineUpdateRequestDto update) {
    Airline updatedAirline = airlineService.updateAirline(id, update);
    return ResponseEntity.ok(updatedAirline);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
    airlineService.deleteAirline(id);
    return ResponseEntity.noContent().build();
  }
}