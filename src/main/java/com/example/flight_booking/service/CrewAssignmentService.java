package com.example.flight_booking.service;

import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.repository.CrewAssignmentRepository;
import com.example.flight_booking.repository.CrewMemberRepository;
import com.example.flight_booking.repository.FlightRepository;
import java.util.List;
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

  public CrewAssignment createCrewAssignment(Long flightId, Long crewMemberId, String duty) {
    Flight flight = flightRepository.findById(flightId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + flightId));
    CrewMember crewMember = crewMemberRepository.findById(crewMemberId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew member not found with id " + crewMemberId));

    CrewAssignment crewAssignment = new CrewAssignment();
    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(duty);
    return crewAssignmentRepository.save(crewAssignment);
  }

  public List<CrewAssignment> getAllCrewAssignments() {
    return crewAssignmentRepository.findAll();
  }

  public CrewAssignment getCrewAssignmentById(Long id) {
    return crewAssignmentRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew assignment not found with id " + id));
  }

  public CrewAssignment updateCrewAssignment(Long id, Long flightId, Long crewMemberId, String duty) {
    CrewAssignment crewAssignment = getCrewAssignmentById(id);
    Flight flight = flightRepository.findById(flightId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + flightId));
    CrewMember crewMember = crewMemberRepository.findById(crewMemberId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew member not found with id " + crewMemberId));

    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(duty);
    return crewAssignmentRepository.save(crewAssignment);
  }

  public void deleteCrewAssignment(Long id) {
    CrewAssignment crewAssignment = getCrewAssignmentById(id);
    crewAssignmentRepository.delete(crewAssignment);
  }

}
