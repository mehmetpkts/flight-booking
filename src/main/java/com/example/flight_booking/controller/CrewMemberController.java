package com.example.flight_booking.controller;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.service.CrewMemberService;
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
@RequestMapping("/api/crew-members")
public class CrewMemberController {

  private final CrewMemberService crewMemberService;

  public CrewMemberController(CrewMemberService crewMemberService) {
    this.crewMemberService = crewMemberService;
  }


  @GetMapping("/{id}")
  public ResponseEntity<CrewMemberFilterResponseDto> getCrewMemberById(@PathVariable Long id) {
    return ResponseEntity.ok(crewMemberService.getCrewMemberById(id));
  }

  @PostMapping
  public ResponseEntity<CrewMember> createCrewMember(@Valid @RequestBody CrewMemberCreateRequestDto create) {
    CrewMember crewMember = crewMemberService.createCrewMember(create);
    return ResponseEntity.status(HttpStatus.CREATED).body(crewMember);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id, @Valid @RequestBody CrewMemberUpdateRequestDto update) {
    CrewMember updatedCrewMember = crewMemberService.updateCrewMember(id, update);
    return ResponseEntity.ok(updatedCrewMember);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {
    crewMemberService.deleteCrewMember(id);
    return ResponseEntity.noContent().build();
  }

}
