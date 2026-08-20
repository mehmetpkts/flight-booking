package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.service.PassengerService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/passengers")
public class PassengerController {

  private final PassengerService passengerService;

  public PassengerController(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<PassengerFilterResponseDto> getPassengerById(@PathVariable Long id) {
    return ResponseEntity.ok(passengerService.getPassengerById(id));
  }

  @PostMapping
  public ResponseEntity<Passenger> createPassenger(
      @Valid @RequestBody PassengerCreateRequestDto create) {
    Passenger savedPassenger = passengerService.createPassenger(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerUpdateRequestDto update) {
    Passenger updatedPassenger = passengerService.updatePassenger(id, update);
    return ResponseEntity.ok(updatedPassenger);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
    passengerService.deletePassenger(id);
    return ResponseEntity.noContent().build();
  }

}
