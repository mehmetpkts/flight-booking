package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.flight_booking.repository.PassengerRepository;
import com.example.flight_booking.entity.Passenger;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

  private final PassengerRepository passengerRepository;

  public PassengerController(PassengerRepository passengerRepository) {
    this.passengerRepository = passengerRepository;
  }

  // CRUD işlemleri

  @GetMapping()
  public List<Passenger> getAllPassengers() {
    return passengerRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
    var passenger = passengerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Passenger not found with id " + id));
    return ResponseEntity.ok(passenger);
  }

  record CreatePassengerPayload(
      String firstName,
      String lastName,
      String passportNumber,
      String email,
      String phoneNumber,
      String nationality) {
  };

  @PostMapping()
  public ResponseEntity<Passenger> createPassenger(@RequestBody CreatePassengerPayload payload) {
    Passenger passenger = new Passenger();
    passenger.setFirstName(payload.firstName());
    passenger.setLastName(payload.lastName());
    passenger.setPassportNumber(payload.passportNumber());
    passenger.setEmail(payload.email());
    passenger.setPhone(payload.phoneNumber());
    passenger.setNationality(payload.nationality());
    passenger = passengerRepository.save(passenger);
    return ResponseEntity.ok(passenger);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody CreatePassengerPayload payload) {
    Passenger passenger = passengerRepository.findById(id).orElse(null);
    if (passenger == null) {
      throw new RuntimeException("Passenger not found with id " + id);
    }
    passenger.setFirstName(payload.firstName());
    passenger.setLastName(payload.lastName());
    passenger.setPassportNumber(payload.passportNumber());
    passenger.setEmail(payload.email());
    passenger.setPhone(payload.phoneNumber());
    passenger.setNationality(payload.nationality());
    passenger = passengerRepository.save(passenger);
    return ResponseEntity.ok(passenger);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
    Passenger passenger = passengerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Passenger not found with id " + id));
    passengerRepository.delete(passenger);
    return ResponseEntity.noContent().build();
  }

}
