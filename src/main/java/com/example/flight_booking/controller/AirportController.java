package com.example.flight_booking.controller;


import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.service.AirportService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
  private static final Logger logger = LoggerFactory.getLogger(AirportController.class);

  @GetMapping("/{id}")
  public ResponseEntity<AirportFilterResponseDto> getAirportById(@PathVariable Long id) {

    logger.info("Id'ye göre havalimanı getirme isteği oluşturuldu! airportId={}", id);
    AirportFilterResponseDto airport = airportService.getAirportById(id);
    logger.info("Havalimanı getirildi! airportId = {}", id);
    return ResponseEntity.ok(airport);
  }


  @PostMapping
  public ResponseEntity<Airport> createAirport(@Valid @RequestBody AirportCreateRequestDto create) {

    logger.info("havalimanı oluşturma isteği alındı.");
    Airport savedAirline = airportService.createAirport(create);
    logger.info("Havalimanı oluşturuldu!");
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirline);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
    logger.info("Havalimanı silme isteği alındı. airportId={}", id);
    airportService.deleteAirport(id);
    logger.info("Havalimanı silindi! airportId={}", id);
    return ResponseEntity.noContent().build();
  }

}
