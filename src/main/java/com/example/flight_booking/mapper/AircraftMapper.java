package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import org.springframework.stereotype.Component;

@Component
public class AircraftMapper {

  public AircraftFilterResponseDto toFilterResponseDto(Aircraft aircraft) {
    AircraftFilterResponseDto dto = new AircraftFilterResponseDto();
    dto.setAirlineId(aircraft.getAirline().getAirlineId());
    dto.setAirlineName(aircraft.getAirline().getName());
    dto.setCapacity(aircraft.getCapacity());
    dto.setManufacturer(aircraft.getManufacturer());
    dto.setModel(aircraft.getModel());
    dto.setStatus(aircraft.getStatus().name());
    return dto;
  }

  public Aircraft toEntity(AircraftCreateRequestDto createRequest, Airline airline) {
    Aircraft aircraft = new Aircraft();
    aircraft.setModel(createRequest.getModel());
    aircraft.setManufacturer(createRequest.getManufacturer());
    aircraft.setCapacity(createRequest.getCapacity());
    aircraft.setStatus(createRequest.getStatus());
    aircraft.setAirline(airline);
    return aircraft;
  }

  public void updateEntity(Aircraft aircraft, AircraftUpdateRequestDto updateRequest) {
    aircraft.setModel(updateRequest.getModel());
    aircraft.setManufacturer(updateRequest.getManufacturer());
    aircraft.setCapacity(updateRequest.getCapacity());
    aircraft.setStatus(updateRequest.getStatus());
  }
}
