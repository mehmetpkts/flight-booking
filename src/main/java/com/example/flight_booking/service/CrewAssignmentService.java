package com.example.flight_booking.service;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.repository.CrewAssignmentRepository;
import com.example.flight_booking.repository.CrewMemberRepository;
import com.example.flight_booking.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CrewAssignmentService {

  private final CrewAssignmentRepository crewAssignmentRepository;
  private final FlightRepository flightRepository;
  private final CrewMemberRepository crewMemberRepository;

  public CrewAssignmentService(CrewAssignmentRepository crewAssignmentRepository,
      FlightRepository flightRepository,
      CrewMemberRepository crewMemberRepository) {
    this.crewAssignmentRepository = crewAssignmentRepository;
    this.flightRepository = flightRepository;
    this.crewMemberRepository = crewMemberRepository;
  }

  public CrewAssignment getCrewAssignmentEntityById(Long id) {
    return crewAssignmentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew assignment not found with id " + id));
  }

  private Flight getFlightEntityById(Long id) {
    return flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + id));
  }

  private CrewMember getCrewMemberEntityById(Long id) {
    return crewMemberRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew member not found with id " + id));
  }

  public CrewAssignmentFilterResponseDto getCrewAssignmentById(Long id) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    CrewAssignmentFilterResponseDto dto = new CrewAssignmentFilterResponseDto();
    dto.setAssignmentId(crewAssignment.getAssignmentId());
    dto.setFlight(crewAssignment.getFlight());
    dto.setCrewMember(crewAssignment.getCrewMember());
    dto.setDuty(crewAssignment.getDuty());
    return dto;
  }

  public CrewAssignment createCrewAssignment(CrewAssignmentCreateRequestDto create) {
    Flight flight = getFlightEntityById(create.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(create.getCrewMemberId());

    CrewAssignment crewAssignment = new CrewAssignment();
    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(create.getDuty());
    return crewAssignmentRepository.save(crewAssignment);
  }


  public CrewAssignment updateCrewAssignment(Long id, CrewAssignmentUpdateRequestDto update) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    Flight flight = getFlightEntityById(update.getFlightId());
    CrewMember crewMember = getCrewMemberEntityById(update.getCrewMemberId());

    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(update.getDuty());
    return crewAssignmentRepository.save(crewAssignment);
  }

  public void deleteCrewAssignment(Long id) {
    CrewAssignment crewAssignment = getCrewAssignmentEntityById(id);
    crewAssignmentRepository.delete(crewAssignment);
  }


}
