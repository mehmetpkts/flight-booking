package com.example.flight_booking.controller;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.service.FlightService;
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
@RequestMapping("/api/flights")
public class FlightController {

  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

//  @GetMapping
//  public List<Flight> getAllFlights() {
//    return flightService.getAllFlights();
//  }

  @GetMapping("/{id}")
  public ResponseEntity<FlightFilterResponseDto> getFlightById(@PathVariable Long id) {
    return ResponseEntity.ok(flightService.getFlightById(id));
  }

  @PostMapping
  public ResponseEntity<Flight> createFlight(@Valid @RequestBody FlightCreateRequestDto create) {
    Flight createdFlight = flightService.createFlight(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightUpdateRequestDto update) {
    Flight updatedFlight = flightService.updateFlight(id, update);
    return ResponseEntity.ok(updatedFlight);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
    flightService.deleteFlight(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/iataCode")
  public List<Flight> iataFlights(@RequestBody FlightIataCodeRequestDto flightIataCodeRequestDto){
    return flightService.getByIataCode(flightIataCodeRequestDto);
  }

  @PostMapping("/filter")
  public List<Flight> filterFlights(@RequestBody FlightFilterRequestDto filterRequestDto) {
    return flightService.getByArrivalAndDepartureCitiesAndStatus(filterRequestDto);
  }
}
