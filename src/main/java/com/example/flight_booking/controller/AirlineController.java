package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
  private final AirlineService airlineService;

  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }
  private static final Logger logger = LoggerFactory.getLogger(AirlineController.class);

  @GetMapping
  public ResponseEntity<List<AirlineFilterResponseDto>> getAllAirlines() {

    logger.info("Liste olarak havayolu getirme isteği alındı.");

    List<AirlineFilterResponseDto> airlines = airlineService.getAllAirlines();

    logger.info("Liste başarı ile getirildi!");

    return ResponseEntity.ok(airlines);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AirlineFilterResponseDto> getAirlineById(@PathVariable Long id) {

    logger.info("id'ye göre havayolu çekilme isteği alındı. airlineId = {}",id);
    AirlineFilterResponseDto airline = airlineService.getAirlineById(id);
    logger.info("Havayolu başarı ile çekildi! airlineId = {}", id);

    return ResponseEntity.ok(airline);
  }

  @PostMapping
  public ResponseEntity<Airline> createAirline(@Valid @RequestBody AirlineCreateRequestDto create) {

    logger.info("Havayolu oluşturma isteği alındı.");
    Airline savedAirline = airlineService.createAirline(create);
    logger.info("Havayolu oluştruldu!");
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAirline);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
    logger.info("Havayolu silme isteği alındı. airlineId = {}", id);
    airlineService.deleteAirline(id);
    logger.info("Havayolu silindi.airlineId = {}", id);
    return ResponseEntity.noContent().build();
  }
}