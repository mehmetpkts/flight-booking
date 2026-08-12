package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.constraints.NotEmpty;
import com.example.flight_booking.service.PassengerService;
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

  private final PassengerService passengerService;

  public PassengerController(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  // CRUD işlemleri

  @GetMapping()
  public List<Passenger> getAllPassengers() {
    return passengerService.getAllPassengers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
    return ResponseEntity.ok(passengerService.getPassengerById(id));
  }

  record CreatePassengerPayload(
      @NotEmpty(message = "Passenger first name must not be empty") String firstName,
      @NotEmpty(message = "Passenger last name must not be empty") String lastName,
      @NotEmpty(message = "Passenger passport number must not be empty") String passportNumber,
      @NotEmpty(message = "Passenger email must not be empty") String email,
      @NotEmpty(message = "Passenger phone number must not be empty") String phoneNumber,
      @NotEmpty(message = "Passenger nationality must not be empty") String nationality) {
  };

  @PostMapping()
  public ResponseEntity<Passenger> createPassenger(@RequestBody CreatePassengerPayload payload) {
    Passenger savedPassenger = passengerService.createPassenger(
        payload.firstName(),
        payload.lastName(),
        payload.passportNumber(),
        payload.email(),
        payload.phoneNumber(),
        payload.nationality());
    return ResponseEntity.ok(savedPassenger);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody CreatePassengerPayload payload) {
    Passenger updatedPassenger = passengerService.updatePassenger(
        id,
        payload.firstName(),
        payload.lastName(),
        payload.passportNumber(),
        payload.email(),
        payload.phoneNumber(),
        payload.nationality());
    return ResponseEntity.ok(updatedPassenger);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
    passengerService.deletePassenger(id);
    return ResponseEntity.noContent().build();
  }

}
