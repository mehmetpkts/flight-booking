package com.example.flight_booking.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.flight_booking.service.CrewMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/crew-members")
public class CrewMemberController {

  private final CrewMemberService crewMemberService;

  public CrewMemberController(CrewMemberService crewMemberService) {
    this.crewMemberService = crewMemberService;
  }

  // crud işlemleri

  @GetMapping()
  public List<CrewMember> getAllCrewMembers() {
    return crewMemberService.getAllCrewMembers();
  }

  // Id ile okuma:

  @GetMapping("/{id}")
  public CrewMember getCrewMemberById(@PathVariable Long id) {
    return crewMemberService.getCrewMemberById(id);
  }

  // Oluşturma - Kaydetme:

  record CreateCrewMemberPayload(@NotEmpty String firstName,
      @NotEmpty String lastName,
      @NotEmpty String role,
      @NotEmpty Integer employeeNumber,
      @NotEmpty String phone,
      @NotEmpty Airline airlineId) {
  };

  @PostMapping()
  public ResponseEntity<CrewMember> createCrewMember(@Valid @RequestBody CreateCrewMemberPayload payload) {
    CrewMember crewMember = crewMemberService.createCrewMember(payload.firstName(),
        payload.lastName(),
        payload.role(),
        payload.employeeNumber(),
        payload.phone(),
        payload.airlineId());
    return ResponseEntity.ok(crewMember);
  }

  // Güncelleme

  @PutMapping("/{id}")
  public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id,
      @Valid @RequestBody CreateCrewMemberPayload payload) {
    CrewMember updatedCrewMember = crewMemberService.updateCrewMember(id, payload.firstName(),
        payload.lastName(),
        payload.role(),
        payload.employeeNumber(),
        payload.phone(),
        payload.airlineId());
    if (updatedCrewMember != null) {
      return ResponseEntity.ok(updatedCrewMember);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Silme

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
    crewMemberService.deleteCrewMember(id);
    return ResponseEntity.noContent().build();
  }

}
