package com.example.flight_booking.service;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.exception.BusinessRuleException;
import com.example.flight_booking.exception.DuplicateResourceException;
import com.example.flight_booking.exception.ResourceNotFoundException;
import com.example.flight_booking.mapper.CrewAssignmentMapper;
import com.example.flight_booking.repository.CrewAssignmentRepository;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrewAssignmentService {

  private static final Logger logger = LoggerFactory.getLogger(CrewAssignmentService.class);
  private final CrewAssignmentRepository crewAssignmentRepository;
  private final FlightService flightService;
  private final CrewMemberService crewMemberService;
  private final CrewAssignmentMapper crewAssignmentMapper;

  public CrewAssignmentService(CrewAssignmentRepository crewAssignmentRepository,
                               FlightService flightService,
                               CrewMemberService crewMemberService,
                               CrewAssignmentMapper crewAssignmentMapper) {
    this.crewAssignmentRepository = crewAssignmentRepository;
    this.flightService = flightService;
    this.crewMemberService = crewMemberService;
    this.crewAssignmentMapper = crewAssignmentMapper;
  }

  public CrewAssignment getCrewAssignmentEntityById(Long id) {
    logger.debug("Crew ataması aranıyor. assignmentId={}", id);
    return crewAssignmentRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Crew ataması bulunamadı. assignmentId={}", id);
          return new ResourceNotFoundException("Crew assignment", id);
        });
  }

  private Flight getFlightEntityById(Long id) {
    return flightService.getFlightEntityById(id);
  }

  private CrewMember getCrewMemberEntityById(Long id) {
    return crewMemberService.getCrewMemberEntityById(id);
  }

  public CrewAssignmentFilterResponseDto getCrewAssignmentById(Long id) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    return crewAssignmentMapper.toFilterResponseDto(crewAssignment);
  }

  public CrewAssignment createCrewAssignment(CrewAssignmentCreateRequestDto create) {
    logger.info("Crew ataması oluşturuluyor. flightId={}, crewMemberId={}, duty={}",
        create.getFlightId(), create.getCrewMemberId(), create.getDuty());

    Flight flight = getFlightEntityById(create.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(create.getCrewMemberId());
    validateCrewAssignmentEligibility(flight, crewMember);
    validateCrewMemberIsNotAssignedToFlight(flight.getFlightId(), crewMember.getCrewMemberId());

    CrewAssignment crewAssignment = crewAssignmentMapper.toEntity(create, flight, crewMember);
    CrewAssignment savedCrewAssignment = crewAssignmentRepository.save(crewAssignment);

    logger.info("Crew ataması oluşturuldu. assignmentId={}, flightId={}, crewMemberId={}",
        savedCrewAssignment.getAssignmentId(), flight.getFlightId(), crewMember.getCrewMemberId());
    return savedCrewAssignment;
  }


  public CrewAssignment updateCrewAssignment(Long id, CrewAssignmentUpdateRequestDto update) {
    logger.info("Crew ataması güncelleniyor. assignmentId={}, flightId={}, crewMemberId={}",
        id, update.getFlightId(), update.getCrewMemberId());

    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    Flight flight = getFlightEntityById(update.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(update.getCrewMemberId());
    validateCrewAssignmentEligibility(flight, crewMember);
    validateCrewMemberIsNotAssignedToFlight(
        flight.getFlightId(), crewMember.getCrewMemberId(), crewAssignment.getAssignmentId());

    crewAssignmentMapper.updateEntity(crewAssignment, update, flight, crewMember);
    CrewAssignment savedCrewAssignment = crewAssignmentRepository.save(crewAssignment);

    logger.info("Crew ataması güncellendi. assignmentId={}", savedCrewAssignment.getAssignmentId());
    return savedCrewAssignment;
  }

  public void deleteCrewAssignment(Long id) {
    logger.info("Crew ataması siliniyor. assignmentId={}", id);

    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    crewAssignmentRepository.delete(crewAssignment);

    logger.info("Crew ataması silindi. assignmentId={}", id);
  }

  private void validateCrewAssignmentEligibility(Flight flight, CrewMember crewMember) {
    if (!Objects.equals(flight.getAirline().getAirlineId(),
        crewMember.getAirline().getAirlineId())) {
      throw new BusinessRuleException(
          "Crew member id " + crewMember.getCrewMemberId()
              + " does not belong to the airline operating flight id " + flight.getFlightId());
    }
  }

  private void validateCrewMemberIsNotAssignedToFlight(Long flightId, Long crewMemberId) {
    if (crewAssignmentRepository.existsByFlight_FlightIdAndCrewMember_CrewMemberId(
        flightId, crewMemberId)) {
      throw duplicateCrewAssignmentException(crewMemberId, flightId);
    }
  }

  private void validateCrewMemberIsNotAssignedToFlight(
      Long flightId, Long crewMemberId, Long assignmentId) {
    if (crewAssignmentRepository
        .existsByFlight_FlightIdAndCrewMember_CrewMemberIdAndAssignmentIdNot(
            flightId, crewMemberId, assignmentId)) {
      throw duplicateCrewAssignmentException(crewMemberId, flightId);
    }
  }

  private DuplicateResourceException duplicateCrewAssignmentException(Long crewMemberId, Long flightId) {
    return new DuplicateResourceException(
        "Crew member id " + crewMemberId + " is already assigned to flight id " + flightId);
  }

}
