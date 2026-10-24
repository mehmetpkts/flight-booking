package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.entity.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {

  AirportFilterResponseDto toFilterResponseDto(Airport airport);

  Airport toEntity(AirportCreateRequestDto createRequest);
}
