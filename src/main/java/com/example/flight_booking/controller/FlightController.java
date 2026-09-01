package com.example.flight_booking.controller;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.service.FlightService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/flights")
public class FlightController {

  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }
  private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
//  @GetMapping
//  public List<Flight> getAllFlights() {
//    return flightService.getAllFlights();
//  }

  @GetMapping("/{id}")
  public ResponseEntity<FlightFilterResponseDto> getFlightById(@PathVariable Long id) {

    logger.info("id uçuş isteği oluşturuldu.");
    FlightFilterResponseDto flight = flightService.getFlightById(id);
    logger.info("id ile uçuş verisi getirildi!");

    return ResponseEntity.ok(flight);
  }

  @PostMapping
  public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightCreateRequestDto create) {

    logger.info("Yeni uçuş oluşturma isteği oluşturuldu.");
    Flight createdFlight = flightService.createFlight(create);
    logger.info("Yeni uçuş oluşturuldu!");

    return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightUpdateRequestDto update) {

    logger.info("Uçuş özellikleri güncelleme isteği oluşturuldu.");
    Flight updatedFlight = flightService.updateFlight(id, update);
    logger.info("Uçuş özellikleri güncellendi.");

    return ResponseEntity.ok(updatedFlight);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {

    logger.info("Uçuş silme isteği oluşturuldu.");
    flightService.deleteFlight(id);
    logger.info("Uçuş silindi.");
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/iataCode")
  public List<Flight> iataFlights(@RequestBody FlightIataCodeRequestDto requestDto){

    logger.info("IATA koduyla uçuş sorgusu alındı: {} ", requestDto);
    List<Flight> flights = flightService.getByIataCode(requestDto);
    logger.info("IATA sorgusu tamamlandı");
    return flights;
  }

  @PostMapping("/filter")
  public List<Flight> filterFlights(@RequestBody FlightFilterRequestDto filterRequestDto) {

    logger.info("Uçuştaki filtreleme metodu istek alındı.");
    List<Flight> flights = flightService.getByArrivalAndDepartureCitiesAndStatus(filterRequestDto);
    logger.info("Filtreleme sonuçları alındı!");

    return flights;
  }
}
