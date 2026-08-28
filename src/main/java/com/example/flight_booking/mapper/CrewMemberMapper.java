package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberMapper {

  public CrewMemberFilterResponseDto toFilterResponseDto(CrewMember crewMember) {
    CrewMemberFilterResponseDto dto = new CrewMemberFilterResponseDto();
    dto.setAirline(crewMember.getAirline());
    dto.setEmployeeNumber(crewMember.getEmployeeNumber());
    dto.setFirstName(crewMember.getFirstName());
    dto.setLastName(crewMember.getLastName());
    dto.setPhone(crewMember.getPhone());
    dto.setRole(crewMember.getRole());
    return dto;
  }

  public CrewMember toEntity(CrewMemberCreateRequestDto createRequest, Airline airline) {
    CrewMember crewMember = new CrewMember();
    crewMember.setFirstName(createRequest.getFirstName());
    crewMember.setLastName(createRequest.getLastName());
    crewMember.setRole(createRequest.getRole());
    crewMember.setEmployeeNumber(createRequest.getEmployeeNumber());
    crewMember.setPhone(createRequest.getPhone());
    crewMember.setAirline(airline);
    return crewMember;
  }

  public void updateEntity(
      CrewMember crewMember,
      CrewMemberUpdateRequestDto updateRequest,
      Airline airline) {
    crewMember.setRole(updateRequest.getRole());
    crewMember.setEmployeeNumber(updateRequest.getEmployeeNumber());
    crewMember.setPhone(updateRequest.getPhone());
    crewMember.setAirline(airline);
  }
}
