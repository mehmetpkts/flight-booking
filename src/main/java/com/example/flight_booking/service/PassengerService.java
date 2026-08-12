package com.example.flight_booking.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.flight_booking.repository.PassengerRepository;
import com.example.flight_booking.entity.Passenger;

@Service
public class PassengerService {

  private final PassengerRepository passengerRepository;

  public PassengerService(PassengerRepository passengerRepository) {
    this.passengerRepository = passengerRepository;
  }

  // oluşturma - kaydetme
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

  // okuma - listeleme
  public List<Passenger> getAllPassengers() {
    return passengerRepository.findAll();
  }

  // id okuma
  public Passenger getPassengerById(Long id) {
    return passengerRepository.findById(id).orElse(null);
  }

  // güncelleme
  public Passenger updatePassenger(Long id, String firstName, String lastName, String passportNumber, String email,
      String phoneNumber, String nationality) {
    Passenger passenger = passengerRepository.findById(id).orElse(null);
    if (passenger != null) {
      passenger.setFirstName(firstName);
      passenger.setLastName(lastName);
      passenger.setPassportNumber(passportNumber);
      passenger.setEmail(email);
      passenger.setPhone(phoneNumber);
      passenger.setNationality(nationality);
      return passengerRepository.save(passenger);
    }
    return null;
  }

  // silme

  public void deletePassenger(Long id) {
    passengerRepository.deleteById(id);
  }
}
