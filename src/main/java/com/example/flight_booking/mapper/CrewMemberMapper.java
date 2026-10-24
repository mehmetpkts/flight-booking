package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.CrewMember.CrewMemberCreateRequestDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberFilterResponseDto;
import com.example.flight_booking.dto.CrewMember.CrewMemberUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.CrewMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CrewMemberMapper {

  CrewMemberFilterResponseDto toFilterResponseDto(CrewMember crewMember);

  @Mapping(target = "airline", source = "airline")
  @Mapping(target = "firstName", source = "createRequest.firstName")
  @Mapping(target = "lastName", source = "createRequest.lastName")
  @Mapping(target = "role", source = "createRequest.role")
  @Mapping(target = "employeeNumber", source = "createRequest.employeeNumber")
  @Mapping(target = "phone", source = "createRequest.phone")
  CrewMember toEntity(CrewMemberCreateRequestDto createRequest, Airline airline);

  @Mapping(target = "role", source = "updateRequest.role")
  @Mapping(target = "employeeNumber", source = "updateRequest.employeeNumber")
  @Mapping(target = "phone", source = "updateRequest.phone")
  @Mapping(target = "airline", source = "airline")
  void updateEntity(
      @MappingTarget CrewMember crewMember,
      CrewMemberUpdateRequestDto updateRequest,
      Airline airline);
}
