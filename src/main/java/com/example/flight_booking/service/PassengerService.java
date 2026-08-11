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
  public void createPassenger(Passenger passenger) {
    passengerRepository.save(passenger);
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
  public void updatePassenger(Passenger passenger) {
    passengerRepository.save(passenger);
  }

  // silme

  public void deletePassenger(Long id) {
    passengerRepository.deleteById(id);
  }
}
