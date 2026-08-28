package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

  public AirportFilterResponseDto toFilterResponseDto(Airport airport) {
    AirportFilterResponseDto dto = new AirportFilterResponseDto();
    dto.setName(airport.getName());
    dto.setCity(airport.getCity());
    dto.setCountry(airport.getCountry());
    dto.setIataCode(airport.getIataCode());
    return dto;
  }

  public Airport toEntity(AirportCreateRequestDto createRequest) {
    Airport airport = new Airport();
    airport.setName(createRequest.getName());
    airport.setCountry(createRequest.getCountry());
    airport.setCity(createRequest.getCity());
    airport.setIataCode(createRequest.getIataCode());
    return airport;
  }
}
