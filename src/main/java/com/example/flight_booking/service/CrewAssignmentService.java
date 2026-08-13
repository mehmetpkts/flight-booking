package com.example.flight_booking.service;

import com.example.flight_booking.repository.CrewAssignmentRepository;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.CrewMember;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrewAssignmentService {

  private final CrewAssignmentRepository crewAssignmentRepository;

  public CrewAssignmentService(CrewAssignmentRepository crewAssignmentRepository) {
    this.crewAssignmentRepository = crewAssignmentRepository;
  }

  // Oluşturma

  public CrewAssignment createCrewAssignment(Flight flight, CrewMember crewMember, String duty) {
    CrewAssignment crewAssignment = new CrewAssignment();
    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(duty);
    return crewAssignmentRepository.save(crewAssignment);
  }

  // Okuma

  public List<CrewAssignment> getAllCrewAssignments() {
    return crewAssignmentRepository.findAll();
  }

  // id ile okuma

  public CrewAssignment getCrewAssignmentById(Long id) {
    return crewAssignmentRepository.findById(id).orElse(null);
  }

  // Güncelleme

  public CrewAssignment updateCrewAssignment(Long id, Flight flight, CrewMember crewMember, String duty) {
    CrewAssignment crewAssignment = crewAssignmentRepository.findById(id).orElse(null);
    if (crewAssignment != null) {
      crewAssignment.setFlight(flight);
      crewAssignment.setCrewMember(crewMember);
      crewAssignment.setDuty(duty);
      return crewAssignmentRepository.save(crewAssignment);
    }
    return null;
  }

  // silme

  public void deleteCrewAssignment(Long id) {
    crewAssignmentRepository.deleteById(id);
  }

}
