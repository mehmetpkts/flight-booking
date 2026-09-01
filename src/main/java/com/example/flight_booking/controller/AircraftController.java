package com.example.flight_booking.controller;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.service.AircraftService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

  private final AircraftService aircraftService;

  public AircraftController(AircraftService aircraftService) {
    this.aircraftService = aircraftService;
  }

  private static final Logger logger = LoggerFactory.getLogger(AircraftController.class);

  @GetMapping("/{id}")
  public ResponseEntity<AircraftFilterResponseDto> getAircraftById(@PathVariable Long id){
    logger.info("Uçak getirme isteği alındı. aircraftId={}", id);

    AircraftFilterResponseDto aircraft = aircraftService.getAircraftById(id);

    logger.info("Uçak başarıyla getirildi. aircraftId={}", id);
    return ResponseEntity.ok(aircraft);
  }


  @PostMapping
  public ResponseEntity<Aircraft> createAircraft(@Valid @RequestBody AircraftCreateRequestDto create) {
    logger.info("Uçak oluşturma isteği alındı.");
    Aircraft createAircraft = aircraftService.createAircraft(create);
    logger.info("Uçak oluşturuldu.");
    return ResponseEntity.status(HttpStatus.CREATED).body(createAircraft);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftUpdateRequestDto update) {
    logger.info("Uçak güncelleme isteği alındı. aircraftId={}", id);
    Aircraft updatedAircraft = aircraftService.updateAircraft(id, update);
    logger.info("Uçak güncellendi.aircraftId={}", id);
    return ResponseEntity.ok(updatedAircraft);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAircraft (@PathVariable Long id){
    logger.info("Uçak silme işlemi alındı. aircraftId={}", id);
    aircraftService.deleteAircraft(id);
    logger.info("Uçal silindi. aircraftId={}", id);
    return ResponseEntity.noContent().build();
  }
}
