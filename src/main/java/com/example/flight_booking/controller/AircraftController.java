package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import com.example.flight_booking.enums.AircraftStatus;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.service.AircraftService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.flight_booking.entity.Aircraft;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

  private final AircraftService aircraftService;

  public AircraftController(AircraftService aircraftService) {
    this.aircraftService = aircraftService;
  }

  // crud işlemleri

  @GetMapping()
  public List<Aircraft> getAllAircrafts() {
    return aircraftService.getAllAircrafts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
    return ResponseEntity.ok(aircraftService.getAircraftById(id));
  }

  record CreateAircraftPayload(
      @NotNull(message = "Model must not be null") String model,
      @NotNull(message = "Manufacturer must not be null") String manufacturer,
      @NotNull(message = "Capacity must not be null") int capacity,
      @NotNull(message = "Status must not be null") AircraftStatus status,
      @NotNull(message = "Airline must not be null") Airline airline) {
  }

  @PostMapping()
  public ResponseEntity<Aircraft> createAircraft(@Valid @RequestBody CreateAircraftPayload payload) {
    Aircraft savedAircraft = aircraftService.createAircraft(
        payload.model(),
        payload.manufacturer(),
        payload.capacity(),
        payload.status(),
        payload.airline());
    return ResponseEntity.ok(savedAircraft);

  }

  @PutMapping("/{id}")
  public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id,
      @Valid @RequestBody CreateAircraftPayload payload) {
    Aircraft updatedAircraft = aircraftService.updateAircraft(
        id,
        payload.model(),
        payload.manufacturer(),
        payload.capacity(),
        payload.status(),
        payload.airline());
    return ResponseEntity.ok(updatedAircraft);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
    aircraftService.deleteAircraft(id);
    return ResponseEntity.noContent().build();
  }
}
