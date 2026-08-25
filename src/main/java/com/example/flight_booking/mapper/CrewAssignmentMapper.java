package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class CrewAssignmentMapper {

  public CrewAssignmentFilterResponseDto toFilterResponseDto(CrewAssignment crewAssignment) {
    CrewAssignmentFilterResponseDto dto = new CrewAssignmentFilterResponseDto();
    dto.setAssignmentId(crewAssignment.getAssignmentId());
    dto.setFlight(crewAssignment.getFlight());
    dto.setCrewMember(crewAssignment.getCrewMember());
    dto.setDuty(crewAssignment.getDuty());
    return dto;
  }

  public CrewAssignment toEntity(
      CrewAssignmentCreateRequestDto createRequest,
      Flight flight,
      CrewMember crewMember) {
    CrewAssignment crewAssignment = new CrewAssignment();
    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(createRequest.getDuty());
    return crewAssignment;
  }

  public void updateEntity(
      CrewAssignment crewAssignment,
      CrewAssignmentUpdateRequestDto updateRequest,
      Flight flight,
      CrewMember crewMember) {
    crewAssignment.setFlight(flight);
    crewAssignment.setCrewMember(crewMember);
    crewAssignment.setDuty(updateRequest.getDuty());
  }
}
