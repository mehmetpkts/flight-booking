package com.example.flight_booking.service;

import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.repository.PassengerRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {

  private final PassengerRepository passengerRepository;

  public PassengerService(PassengerRepository passengerRepository) {
    this.passengerRepository = passengerRepository;
  }

  public Passenger createPassenger(String firstName, String lastName, String passportNumber, String email,
      String phoneNumber, String nationality) {
    Passenger passenger = new Passenger();
    passenger.setFirstName(firstName);
    passenger.setLastName(lastName);
    passenger.setPassportNumber(passportNumber);
    passenger.setEmail(email);
    passenger.setPhone(phoneNumber);
    passenger.setNationality(nationality);
    return passengerRepository.save(passenger);
  }

  public List<Passenger> getAllPassengers() {
    return passengerRepository.findAll();
  }

  public Passenger getPassengerById(Long id) {
    return passengerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Passenger not found with id " + id));
  }

  public Passenger updatePassenger(Long id, String firstName, String lastName, String passportNumber, String email,
      String phoneNumber, String nationality) {
    Passenger passenger = getPassengerById(id);
    passenger.setFirstName(firstName);
    passenger.setLastName(lastName);
    passenger.setPassportNumber(passportNumber);
    passenger.setEmail(email);
    passenger.setPhone(phoneNumber);
    passenger.setNationality(nationality);
    return passengerRepository.save(passenger);
  }

  public void deletePassenger(Long id) {
    Passenger passenger = getPassengerById(id);
    passengerRepository.delete(passenger);
  }
}
