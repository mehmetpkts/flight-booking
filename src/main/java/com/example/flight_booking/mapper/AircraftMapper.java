package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

  @Mapping(target = "airlineId", source = "airline.airlineId")
  @Mapping(target = "airlineName", source = "airline.name")
  @Mapping(target = "status", expression = "java(aircraft.getStatus() != null ? aircraft.getStatus().name() : null)")
  AircraftFilterResponseDto toFilterResponseDto(Aircraft aircraft);

  @Mapping(target = "airline", source = "airline")
  @Mapping(target = "status", source = "createRequest.status")
  Aircraft toEntity(AircraftCreateRequestDto createRequest, Airline airline);

  @Mapping(target = "status", source = "updateRequest.status")
  @Mapping(target = "airline", source = "airline")
  void updateEntity(
      @MappingTarget Aircraft aircraft,
      AircraftUpdateRequestDto updateRequest,
      Airline airline);
}
