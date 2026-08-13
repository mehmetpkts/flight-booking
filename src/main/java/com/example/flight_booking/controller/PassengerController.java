package com.example.flight_booking.controller;

import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

  private final PassengerService passengerService;

  public PassengerController(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @GetMapping
  public List<Passenger> getAllPassengers() {
    return passengerService.getAllPassengers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
    return ResponseEntity.ok(passengerService.getPassengerById(id));
  }

  record CreatePassengerPayload(
      @NotBlank(message = "Passenger first name must not be blank") String firstName,
      @NotBlank(message = "Passenger last name must not be blank") String lastName,
      @NotBlank(message = "Passenger passport number must not be blank") String passportNumber,
      @NotBlank(message = "Passenger email must not be blank") String email,
      @NotBlank(message = "Passenger phone number must not be blank") String phoneNumber,
      @NotBlank(message = "Passenger nationality must not be blank") String nationality) {
  }

  @PostMapping
  public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody CreatePassengerPayload payload) {
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
  public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id,
      @Valid @RequestBody CreatePassengerPayload payload) {
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
