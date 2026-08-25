package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.dto.Airline.AirlineUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {

  public AirlineFilterResponseDto toFilterResponseDto(Airline airline) {
    AirlineFilterResponseDto dto = new AirlineFilterResponseDto();
    dto.setName(airline.getName());
    dto.setCountry(airline.getCountry());
    dto.setIataCode(airline.getIataCode());
    return dto;
  }

  public Airline toEntity(AirlineCreateRequestDto createRequest) {
    Airline airline = new Airline();
    airline.setCountry(createRequest.getCountry());
    airline.setIataCode(createRequest.getIataCode());
    airline.setName(createRequest.getName());
    return airline;
  }

  public void updateEntity(Airline airline, AirlineUpdateRequestDto updateRequest) {
    airline.setName(updateRequest.getName());
    airline.setIataCode(updateRequest.getIataCode());
    airline.setCountry(updateRequest.getCountry());
  }
}
