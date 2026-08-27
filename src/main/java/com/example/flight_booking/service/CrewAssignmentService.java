package com.example.flight_booking.service;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.mapper.CrewAssignmentMapper;
import com.example.flight_booking.repository.CrewAssignmentRepository;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CrewAssignmentService {

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
    return crewAssignmentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew assignment not found with id " + id));
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
    Flight flight = getFlightEntityById(create.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(create.getCrewMemberId());
    validateCrewAssignmentEligibility(flight, crewMember);
    validateCrewMemberIsNotAssignedToFlight(flight.getFlightId(), crewMember.getCrewMemberId());

    CrewAssignment crewAssignment = crewAssignmentMapper.toEntity(create, flight, crewMember);
    return crewAssignmentRepository.save(crewAssignment);
  }


  public CrewAssignment updateCrewAssignment(Long id, CrewAssignmentUpdateRequestDto update) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    Flight flight = getFlightEntityById(update.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(update.getCrewMemberId());
    validateCrewAssignmentEligibility(flight, crewMember);
    validateCrewMemberIsNotAssignedToFlight(
        flight.getFlightId(), crewMember.getCrewMemberId(), crewAssignment.getAssignmentId());

    crewAssignmentMapper.updateEntity(crewAssignment, update, flight, crewMember);
    return crewAssignmentRepository.save(crewAssignment);
  }

  public void deleteCrewAssignment(Long id) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    crewAssignmentRepository.delete(crewAssignment);
  }

  private void validateCrewAssignmentEligibility(Flight flight, CrewMember crewMember) {
    if (!Objects.equals(flight.getAirline().getAirlineId(),
        crewMember.getAirline().getAirlineId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
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

  private ResponseStatusException duplicateCrewAssignmentException(Long crewMemberId, Long flightId) {
    return new ResponseStatusException(HttpStatus.CONFLICT,
        "Crew member id " + crewMemberId + " is already assigned to flight id " + flightId);
  }

}
