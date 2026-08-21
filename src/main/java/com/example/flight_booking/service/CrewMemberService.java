package com.example.flight_booking.service;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.repository.CrewMemberRepository;
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

  public CrewMember getCrewMemberEntityById(Long id) {
    return crewMemberRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Crew member not found with id " + id));
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "airline id is not found. id is: " + id));
  }

  public CrewMemberFilterResponseDto getCrewMemberById(Long id){
    CrewMember crewMember = getCrewMemberEntityById(id);

    CrewMemberFilterResponseDto dto = new CrewMemberFilterResponseDto();

    dto.setAirline(crewMember.getAirline());
    dto.setEmployeeNumber(crewMember.getEmployeeNumber());
    dto.setFirstName(crewMember.getFirstName());
    dto.setLastName(crewMember.getLastName());
    dto.setPhone(crewMember.getPhone());
    dto.setRole(crewMember.getRole());

    return dto;
  }

  public CrewMember createCrewMember(CrewMemberCreateRequestDto create) {
    Airline airline = getAirlineEntityById(create.getAirlineId());

    CrewMember crewMember = new CrewMember();
    crewMember.setFirstName(create.getFirstName());
    crewMember.setLastName(create.getLastName());
    crewMember.setRole(create.getRole());
    crewMember.setEmployeeNumber(create.getEmployeeNumber());
    crewMember.setPhone(create.getPhone());
    crewMember.setAirline(airline);
    return crewMemberRepository.save(crewMember);
  }


  public CrewMember updateCrewMember(Long id, CrewMemberUpdateRequestDto update) {
    Airline airline = getAirlineEntityById(update.getAirlineId());
    CrewMember crewMember = getCrewMemberEntityById(id);

    crewMember.setFirstName(update.getFirstName());
    crewMember.setLastName(update.getLastName());
    crewMember.setRole(update.getRole());
    crewMember.setEmployeeNumber(update.getEmployeeNumber());
    crewMember.setPhone(update.getPhone());
    crewMember.setAirline(airline);
    return crewMemberRepository.save(crewMember);
  }

  public void deleteCrewMember(Long id) {
    CrewMember crewMember = getCrewMemberEntityById(id);
    crewMemberRepository.delete(crewMember);
  }

}
