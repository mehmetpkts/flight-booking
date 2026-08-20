package com.example.flight_booking.controller;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.service.CrewAssignmentService;
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
@RequestMapping("/api/crew-assignments")
public class CrewAssignmentController {

  private final CrewAssignmentService crewAssignmentService;

  public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
    this.crewAssignmentService = crewAssignmentService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CrewAssignmentFilterResponseDto> getCrewAssignmentById(@PathVariable Long id) {
    return ResponseEntity.ok(crewAssignmentService.getCrewAssignmentById(id));
  }

  @PostMapping
  public ResponseEntity<CrewAssignment> createCrewAssignment(
      @Valid @RequestBody CrewAssignmentCreateRequestDto create) {
    CrewAssignment crewAssignment = crewAssignmentService.createCrewAssignment(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(crewAssignment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewAssignment> updateCrewAssignment(@PathVariable Long id,
      @Valid @RequestBody CrewAssignmentUpdateRequestDto update) {
    CrewAssignment crewAssignment = crewAssignmentService.updateCrewAssignment(id, update);
    return ResponseEntity.ok(crewAssignment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewAssignment(@PathVariable Long id) {
    crewAssignmentService.deleteCrewAssignment(id);
    return ResponseEntity.noContent().build();
  }

}
