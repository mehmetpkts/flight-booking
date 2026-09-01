package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.service.PassengerService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/passengers")
public class PassengerController {

  private final PassengerService passengerService;

  public PassengerController(PassengerService passengerService) {
    this.passengerService = passengerService;
  }
  private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

  @GetMapping("/{id}")
  public ResponseEntity<PassengerFilterResponseDto> getPassengerById(@PathVariable Long id) {

    logger.info("id'ye göre yolcu isteği alındı. PassengerId: {}", id);
    PassengerFilterResponseDto passenger = passengerService.getPassengerById(id);
    logger.info("id'ye yolcu getirildi. PassengerId: {}", id);
    return ResponseEntity.ok(passenger);
  }

  @PostMapping
  public ResponseEntity<Passenger> createPassenger(
      @Valid @RequestBody PassengerCreateRequestDto create) {

    logger.info("Yolcu oluşturma isteği alındı.");
    Passenger savedPassenger = passengerService.createPassenger(create);
    logger.info("Yolcu oluşturuldu.");

    return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerUpdateRequestDto update) {

    logger.info("Yolcu özellikeleri değiştirme isteği alındı. PassengerId: {}", id);
    Passenger updatedPassenger = passengerService.updatePassenger(id, update);
    logger.info("Yolcu özelliği-leri değiştirildi. PassengerId: {}", id);

    return ResponseEntity.ok(updatedPassenger);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {

    logger.info("Yolcu silinme isteği alındı. PassengerId: {}", id);
    passengerService.deletePassenger(id);
    logger.info("Yolcu silindi! PassengerId: {}", id);

    return ResponseEntity.noContent().build();
  }

}
