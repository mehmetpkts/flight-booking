package com.example.flight_booking.service;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.mapper.PassengerMapper;
import com.example.flight_booking.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {

  private final PassengerRepository passengerRepository;
  private final PassengerMapper passengerMapper;

  public PassengerService(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
    this.passengerRepository = passengerRepository;
    this.passengerMapper = passengerMapper;
  }

  public Passenger getPassengerEntityById(Long id) {
    return passengerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Passenger not defined. Id is: " + id));
  }

  public PassengerFilterResponseDto getPassengerById(Long id) {
    Passenger passenger = getPassengerEntityById(id);
    return passengerMapper.toFilterResponseDto(passenger);
  }

  public Passenger createPassenger(PassengerCreateRequestDto create) {
    Passenger passenger = passengerMapper.toEntity(create);
    return passengerRepository.save(passenger);
  }

  public Passenger updatePassenger(Long id, PassengerUpdateRequestDto update) {
    Passenger passenger = getPassengerEntityById(id);
    passengerMapper.updateEntity(passenger, update);
    return passengerRepository.save(passenger);
  }

  public void deletePassenger(Long id) {
    passengerRepository.delete(getPassengerEntityById(id));
  }
}
