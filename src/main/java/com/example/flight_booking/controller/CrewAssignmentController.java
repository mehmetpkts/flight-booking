package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import com.example.flight_booking.service.CrewAssignmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/crew-assignments")
public class CrewAssignmentController {

  private final CrewAssignmentService crewAssignmentService;

  public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
    this.crewAssignmentService = crewAssignmentService;
  }

  // crud işlemleri

  @GetMapping()
  public List<CrewAssignment> getAllCrewAssignments() {
    return crewAssignmentService.getAllCrewAssignments();
  }

  // Id ile okuma:

  @GetMapping("/{id}")
  public CrewAssignment getCrewAssignmentById(@PathVariable Long id) {
    return crewAssignmentService.getCrewAssignmentById(id);
  }

  // Oluşturma - Kaydetme:

  record CreateCrewAssignmentPayload(@NotEmpty Flight flightId,
      @NotEmpty CrewMember crewMemberId,
      @NotEmpty String duty) {
  };

  @PostMapping()
  public ResponseEntity<CrewAssignment> createCrewAssignment(@Valid @RequestBody CreateCrewAssignmentPayload payload) {
    CrewAssignment crewAssignment = crewAssignmentService.createCrewAssignment(payload.flightId(),
        payload.crewMemberId(),
        payload.duty());
    return ResponseEntity.ok(crewAssignment);
  }

  // Güncelleme

  @PutMapping("/{id}")
  public ResponseEntity<CrewAssignment> updateCrewAssignment(@PathVariable Long id,
      @Valid @RequestBody CreateCrewAssignmentPayload payload) {
    CrewAssignment crewAssignment = crewAssignmentService.updateCrewAssignment(id, payload.flightId(),
        payload.crewMemberId(),
        payload.duty());
    if (crewAssignment != null) {
      return ResponseEntity.ok(crewAssignment);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Silme

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewAssignment(@PathVariable Long id) {
    crewAssignmentService.deleteCrewAssignment(id);
    return ResponseEntity.noContent().build();
  }

}
