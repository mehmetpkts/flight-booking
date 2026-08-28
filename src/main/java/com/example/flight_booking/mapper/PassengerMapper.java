package com.example.flight_booking.mapper;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

  public PassengerFilterResponseDto toFilterResponseDto(Passenger passenger) {
    PassengerFilterResponseDto dto = new PassengerFilterResponseDto();
    dto.setFirstName(passenger.getFirstName());
    dto.setLastName(passenger.getLastName());
    dto.setPassportNumber(passenger.getPassportNumber());
    dto.setEmail(passenger.getEmail());
    dto.setPhoneNumber(passenger.getPhone());
    dto.setNationality(passenger.getNationality());
    return dto;
  }

  public Passenger toEntity(PassengerCreateRequestDto createRequest) {
    Passenger passenger = new Passenger();
    passenger.setFirstName(createRequest.getFirstName());
    passenger.setLastName(createRequest.getLastName());
    passenger.setPassportNumber(createRequest.getPassportNumber());
    passenger.setEmail(createRequest.getEmail());
    passenger.setPhone(createRequest.getPhoneNumber());
    passenger.setNationality(createRequest.getNationality());
    return passenger;
  }

  public void updateEntity(Passenger passenger, PassengerUpdateRequestDto updateRequest) {
    passenger.setEmail(updateRequest.getEmail());
    passenger.setPhone(updateRequest.getPhoneNumber());
  }
}
