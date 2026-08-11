package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.flight_booking.repository.AirportRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.flight_booking.entity.Airport;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

  private final AirportRepository airportRepository;

  public AirportController(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  // crud işlemlerii

  @GetMapping()
  public List<Airport> getAllAirports() {
    return airportRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
    var airport = airportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Airport not found with id " + id));
    return ResponseEntity.ok(airport);
  }

  record CreateAirportPayload(
      String name,
      String city,
      String country,
      String iataCode) {
  };

  @PostMapping
  public ResponseEntity<Airport> createAirport(@RequestBody CreateAirportPayload payload) {
    Airport airport = new Airport();
    airport.setName(payload.name());
    airport.setCity(payload.city());
    airport.setCountry(payload.country());
    airport.setIataCode(payload.iataCode());
    return ResponseEntity.ok(airportRepository.save(airport));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody CreateAirportPayload payload) {
    Airport airport = airportRepository.findById(id).orElse(null);
    if (airport != null) {
      airport.setName(payload.name());
      airport.setCity(payload.city());
      airport.setCountry(payload.country());
      airport.setIataCode(payload.iataCode());
      return ResponseEntity.ok(airportRepository.save(airport));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
    var airport = airportRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Airport not found with id " + id));
    airportRepository.delete(airport);
    return ResponseEntity.noContent().build();
  }

}
