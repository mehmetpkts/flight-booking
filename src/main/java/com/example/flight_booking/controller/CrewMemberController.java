package com.example.flight_booking.controller;

import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.service.CrewMemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
@RequestMapping("/api/crew-members")
public class CrewMemberController {

  private final CrewMemberService crewMemberService;

  public CrewMemberController(CrewMemberService crewMemberService) {
    this.crewMemberService = crewMemberService;
  }

  @GetMapping
  public List<CrewMember> getAllCrewMembers() {
    return crewMemberService.getAllCrewMembers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CrewMember> getCrewMemberById(@PathVariable Long id) {
    return ResponseEntity.ok(crewMemberService.getCrewMemberById(id));
  }

  record CreateCrewMemberPayload(
      @NotBlank(message = "First name must not be blank") String firstName,
      @NotBlank(message = "Last name must not be blank") String lastName,
      @NotBlank(message = "Role must not be blank") String role,
      @NotNull(message = "Employee number must not be null") Integer employeeNumber,
      @NotBlank(message = "Phone must not be blank") String phone,
      @NotNull(message = "Airline ID must not be null") Long airlineId) {
  }

  @PostMapping
  public ResponseEntity<CrewMember> createCrewMember(@Valid @RequestBody CreateCrewMemberPayload payload) {
    CrewMember crewMember = crewMemberService.createCrewMember(
        payload.firstName(),
        payload.lastName(),
        payload.role(),
        payload.employeeNumber(),
        payload.phone(),
        payload.airlineId());
    return ResponseEntity.status(HttpStatus.CREATED).body(crewMember);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id,
      @Valid @RequestBody CreateCrewMemberPayload payload) {
    CrewMember updatedCrewMember = crewMemberService.updateCrewMember(
        id,
        payload.firstName(),
        payload.lastName(),
        payload.role(),
        payload.employeeNumber(),
        payload.phone(),
        payload.airlineId());
    return ResponseEntity.ok(updatedCrewMember);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
    crewMemberService.deleteCrewMember(id);
    return ResponseEntity.noContent().build();
  }

}
