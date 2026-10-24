package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

  AirlineFilterResponseDto toFilterResponseDto(Airline airline);

  Airline toEntity(AirlineCreateRequestDto createRequest);
}
