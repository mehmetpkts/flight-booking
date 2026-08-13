package com.example.flight_booking.controller;

import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.service.CrewAssignmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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

  @GetMapping
  public List<CrewAssignment> getAllCrewAssignments() {
    return crewAssignmentService.getAllCrewAssignments();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CrewAssignment> getCrewAssignmentById(@PathVariable Long id) {
    return ResponseEntity.ok(crewAssignmentService.getCrewAssignmentById(id));
  }

  record CreateCrewAssignmentPayload(
      @NotNull(message = "Flight ID must not be null") Long flightId,
      @NotNull(message = "Crew member ID must not be null") Long crewMemberId,
      @NotBlank(message = "Duty must not be blank") String duty) {
  }

  @PostMapping
  public ResponseEntity<CrewAssignment> createCrewAssignment(@Valid @RequestBody CreateCrewAssignmentPayload payload) {
    CrewAssignment crewAssignment = crewAssignmentService.createCrewAssignment(
        payload.flightId(),
        payload.crewMemberId(),
        payload.duty());
    return ResponseEntity.ok(crewAssignment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewAssignment> updateCrewAssignment(@PathVariable Long id,
      @Valid @RequestBody CreateCrewAssignmentPayload payload) {
    CrewAssignment crewAssignment = crewAssignmentService.updateCrewAssignment(
        id,
        payload.flightId(),
        payload.crewMemberId(),
        payload.duty());
    return ResponseEntity.ok(crewAssignment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewAssignment(@PathVariable Long id) {
    crewAssignmentService.deleteCrewAssignment(id);
    return ResponseEntity.noContent().build();
  }

}
