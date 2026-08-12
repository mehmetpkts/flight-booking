package com.example.flight_booking.service;

import org.springframework.stereotype.Service;
import com.example.flight_booking.repository.AircraftRepository;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.enums.AircraftStatus;
import java.util.List;

@Service
public class AircraftService {

  private final AircraftRepository aircraftRepository;

  public AircraftService(AircraftRepository aircraftRepository) {
    this.aircraftRepository = aircraftRepository;
  }

  // Oluşturma - Kaydetme:

  public Aircraft createAircraft(String model, String manufacturer, int capacity, AircraftStatus status,
      Airline airline) {
    Aircraft aircraft = new Aircraft();
    aircraft.setModel(model);
    aircraft.setManufacturer(manufacturer);
    aircraft.setCapacity(capacity);
    aircraft.setStatus(status);
    aircraft.setAirline(airline);
    return aircraftRepository.save(aircraft);
  }

  // Okuma - Listeleme:

  public List<Aircraft> getAllAircrafts() {
    return aircraftRepository.findAll();
  }

  // Id ile okuma:

  public Aircraft getAircraftById(Long id) {
    return aircraftRepository.findById(id).orElse(null);
  }

  // Güncelleme:

  public Aircraft updateAircraft(Long id, String model, String manufacturer, int capacity, AircraftStatus status,
      Airline airline) {
    Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
    if (aircraft != null) {
      aircraft.setModel(model);
      aircraft.setManufacturer(manufacturer);
      aircraft.setCapacity(capacity);
      aircraft.setStatus(status);
      aircraft.setAirline(airline);
      return aircraftRepository.save(aircraft);
    }
    return null;
  }

  // Silme:

  public void deleteAircraft(Long id) {
    aircraftRepository.deleteById(id);
  }

}
