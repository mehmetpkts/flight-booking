package com.example.flight_booking.controller;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.service.CrewMemberService;
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
@RequestMapping("/api/crew-members")
public class CrewMemberController {

  private final CrewMemberService crewMemberService;

  public CrewMemberController(CrewMemberService crewMemberService) {
    this.crewMemberService = crewMemberService;
  }
  private static final Logger logger = LoggerFactory.getLogger(CrewMemberController.class);

  @GetMapping("/{id}")
  public ResponseEntity<CrewMemberFilterResponseDto> getCrewMemberById(@PathVariable Long id) {

    logger.info("id'ye göre ekip üyesi getirme isteği alındı. CrewMemberId: {}", id);
    CrewMemberFilterResponseDto crewMember = crewMemberService.getCrewMemberById(id);
    logger.info("id'ye göre ekip üyesi getirildi! CrewMemberId: {}", id);

    return ResponseEntity.ok(crewMember);
  }

  @PostMapping
  public ResponseEntity<CrewMember> createCrewMember(@Valid @RequestBody CrewMemberCreateRequestDto create) {

    logger.info("Ekip üyesi oluşturma isteği alındı.");
    CrewMember crewMember = crewMemberService.createCrewMember(create);
    logger.info("Ekip üyesi oluşturuldu.");

    return ResponseEntity.status(HttpStatus.CREATED).body(crewMember);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CrewMember> updateCrewMember(@PathVariable Long id, @Valid @RequestBody CrewMemberUpdateRequestDto update) {

    logger.info("Ekip üyesi özellikleri değiştirme isteği alındı. CrewMemberId: {}", id);
    CrewMember updatedCrewMember = crewMemberService.updateCrewMember(id, update);
    logger.info("Ekip üyesi özelliğiğ-likleri değiştirildi. CrewMemberId: {}", id);

    return ResponseEntity.ok(updatedCrewMember);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCrewMember(@PathVariable Long id) {

    logger.info("Ekip üyesi silme isteği alındı. CrewMemberId: {}", id);
    crewMemberService.deleteCrewMember(id);
    logger.info("Ekip üyesi silindi. CrewMemberId: {}", id);

    return ResponseEntity.noContent().build();
  }

}
