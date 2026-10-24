package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentCreateRequestDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentFilterResponseDto;
import com.example.flight_booking.dto.CrewAssignment.CrewAssignmentUpdateRequestDto;
import com.example.flight_booking.entity.CrewAssignment;
import com.example.flight_booking.entity.CrewMember;
import com.example.flight_booking.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CrewAssignmentMapper {

  CrewAssignmentFilterResponseDto toFilterResponseDto(CrewAssignment crewAssignment);

  @Mapping(target = "flight", source = "flight")
  @Mapping(target = "crewMember", source = "crewMember")
  @Mapping(target = "duty", source = "createRequest.duty")
  CrewAssignment toEntity(
      CrewAssignmentCreateRequestDto createRequest,
      Flight flight,
      CrewMember crewMember);

  @Mapping(target = "flight", source = "flight")
  @Mapping(target = "crewMember", source = "crewMember")
  @Mapping(target = "duty", source = "updateRequest.duty")
  void updateEntity(
      @MappingTarget CrewAssignment crewAssignment,
      CrewAssignmentUpdateRequestDto updateRequest,
      Flight flight,
      CrewMember crewMember);
}
