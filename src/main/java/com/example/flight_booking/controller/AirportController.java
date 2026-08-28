package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.service.AirportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.flight_booking.entity.Airport;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

  private final AirportService airportService;

  public AirportController(AirportService airportService) {
    this.airportService = airportService;
  }


  @GetMapping("/{id}")
  public ResponseEntity<AirportFilterResponseDto> getAirportById(@PathVariable Long id) {
    return ResponseEntity.ok(airportService.getAirportById(id));
  }


  @PostMapping
  public ResponseEntity<Airport> createAirport(@Valid @RequestBody AirportCreateRequestDto create) {
    Airport savedAirline = airportService.createAirport(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirline);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
    airportService.deleteAirport(id);
    return ResponseEntity.noContent().build();
  }

}
