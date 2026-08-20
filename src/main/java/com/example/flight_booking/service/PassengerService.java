package com.example.flight_booking.service;

import com.example.flight_booking.dto.Passenger.PassengerCreateRequestDto;
import com.example.flight_booking.dto.Passenger.PassengerFilterResponseDto;
import com.example.flight_booking.dto.Passenger.PassengerUpdateRequestDto;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.repository.PassengerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {

  private final PassengerRepository passengerRepository;

  public PassengerService(PassengerRepository passengerRepository) {
    this.passengerRepository = passengerRepository;
  }

  private Passenger getPassengerEntityById(Long id) {
    return passengerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Passenger not defined. Id is: " + id));
  }

  public PassengerFilterResponseDto getPassengerById(Long id) {
    Passenger passenger = getPassengerEntityById(id);

    PassengerFilterResponseDto dto = new PassengerFilterResponseDto();
    dto.setFirstName(passenger.getFirstName());
    dto.setLastName(passenger.getLastName());
    dto.setPassportNumber(passenger.getPassportNumber());
    dto.setEmail(passenger.getEmail());
    dto.setPhoneNumber(passenger.getPhone());
    dto.setNationality(passenger.getNationality());

    return dto;
  }

  public Passenger createPassenger(PassengerCreateRequestDto create) {
    Passenger passenger = new Passenger();
    passenger.setFirstName(create.getFirstName());
    passenger.setLastName(create.getLastName());
    passenger.setPassportNumber(create.getPassportNumber());
    passenger.setEmail(create.getEmail());
    passenger.setPhone(create.getPhoneNumber());
    passenger.setNationality(create.getNationality());
    return passengerRepository.save(passenger);
  }

  public Passenger updatePassenger(Long id, PassengerUpdateRequestDto update) {
    Passenger passenger = getPassengerEntityById(id);
    passenger.setFirstName(update.getFirstName());
    passenger.setLastName(update.getLastName());
    passenger.setPassportNumber(update.getPassportNumber());
    passenger.setEmail(update.getEmail());
    passenger.setPhone(update.getPhoneNumber());
    passenger.setNationality(update.getNationality());
    return passengerRepository.save(passenger);
  }

  public void deletePassenger(Long id) {
    passengerRepository.delete(getPassengerEntityById(id));
  }
}
