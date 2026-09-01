package com.example.flight_booking.controller;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.service.CrewAssignmentService;
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
@RequestMapping("/api/crew-assignments")
public class CrewAssignmentController {

  private final CrewAssignmentService crewAssignmentService;

  public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
    this.crewAssignmentService = crewAssignmentService;
  }

  private static final Logger logger = LoggerFactory.getLogger(CrewAssignmentController.class);

  @GetMapping("/{id}")
  public ResponseEntity<CrewAssignmentFilterResponseDto> getCrewAssignmentById(@PathVariable Long id) {

    logger.info("Ekip atam tablosundan id'ye göre veri çekme isteği oluşturuldu.");
    CrewAssignmentFilterResponseDto crewAssignmnet = crewAssignmentService.getCrewAssignmentById(id);
    logger.info("Ekip atam tablosundan id'ye göre veri çekme isteği çekildi.");

    return ResponseEntity.ok(crewAssignmnet);
  }

  @PostMapping
  public ResponseEntity<CrewAssignment> createCrewAssignment(
      @Valid @RequestBody CrewAssignmentCreateRequestDto create) {

    logger.info("Ekip atama tablosuna yeni veri ekleme isteği oluşturuldu.");
    CrewAssignment crewAssignment = crewAssignmentService.createCrewAssignment(create);
    logger.info("Ekip atama tablosuna yeni veri eklendi.");

    return ResponseEntity.status(HttpStatus.CREATED).body(crewAssignment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewAssignment> updateCrewAssignment(@PathVariable Long id,
      @Valid @RequestBody CrewAssignmentUpdateRequestDto update) {

    logger.info("id'ye göre görev atama kısmında güncelleme isteği alındı.");
    CrewAssignment crewAssignment = crewAssignmentService.updateCrewAssignment(id, update);
    logger.info("id'ye göre görev atama kısmında güncelleme gerçekleşti!");
    return ResponseEntity.ok(crewAssignment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewAssignment(@PathVariable Long id) {
    logger.info("id'ye görev atama silinme isteği alındı.");
    crewAssignmentService.deleteCrewAssignment(id);
    logger.info("id'ye göre görev atama silindi!");
    return ResponseEntity.noContent().build();
  }

}
