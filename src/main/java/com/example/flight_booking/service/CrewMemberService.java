package com.example.flight_booking.service;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.repository.CrewMemberRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CrewMemberService {
  private final CrewMemberRepository crewMemberRepository;
  private final AirlineRepository airlineRepository;

  public CrewMemberService(CrewMemberRepository crewMemberRepository, AirlineRepository airlineRepository) {
    this.crewMemberRepository = crewMemberRepository;
    this.airlineRepository = airlineRepository;
  }

  public CrewMember createCrewMember(String firstName, String lastName, String role, Integer employeeNumber,
      String phone, Long airlineId) {
    Airline airline = airlineRepository.findById(airlineId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + airlineId));

    CrewMember crewMember = new CrewMember();
    crewMember.setFirstName(firstName);
    crewMember.setLastName(lastName);
    crewMember.setRole(role);
    crewMember.setEmployeeNumber(employeeNumber);
    crewMember.setPhone(phone);
    crewMember.setAirline(airline);
    return crewMemberRepository.save(crewMember);
  }

  public List<CrewMember> getAllCrewMembers() {
    return crewMemberRepository.findAll();
  }

  public CrewMember getCrewMemberById(Long id) {
    return crewMemberRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew member not found with id " + id));
  }

  public CrewMember updateCrewMember(Long id, String firstName, String lastName, String role, Integer employeeNumber,
      String phone, Long airlineId) {
    CrewMember crewMember = getCrewMemberById(id);
    Airline airline = airlineRepository.findById(airlineId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + airlineId));

    crewMember.setFirstName(firstName);
    crewMember.setLastName(lastName);
    crewMember.setRole(role);
    crewMember.setEmployeeNumber(employeeNumber);
    crewMember.setPhone(phone);
    crewMember.setAirline(airline);
    return crewMemberRepository.save(crewMember);
  }

  public void deleteCrewMember(Long id) {
    CrewMember crewMember = getCrewMemberById(id);
    crewMemberRepository.delete(crewMember);
  }

}
