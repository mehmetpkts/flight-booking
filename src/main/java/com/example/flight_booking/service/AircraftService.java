package com.example.flight_booking.service;

import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.enums.AircraftStatus;
import com.example.flight_booking.repository.AircraftRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AircraftService {

  private final AircraftRepository aircraftRepository;

  public AircraftService(AircraftRepository aircraftRepository) {
    this.aircraftRepository = aircraftRepository;
  }

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

  public List<Aircraft> getAllAircrafts() {
    return aircraftRepository.findAll();
  }

  public Aircraft getAircraftById(Long id) {
    return aircraftRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Aircraft not found with id " + id));
  }

  public Aircraft updateAircraft(Long id, String model, String manufacturer, int capacity, AircraftStatus status,
      Airline airline) {
    Aircraft aircraft = getAircraftById(id);
    aircraft.setModel(model);
    aircraft.setManufacturer(manufacturer);
    aircraft.setCapacity(capacity);
    aircraft.setStatus(status);
    aircraft.setAirline(airline);
    return aircraftRepository.save(aircraft);
  }

  public void deleteAircraft(Long id) {
    Aircraft aircraft = getAircraftById(id);
    aircraftRepository.delete(aircraft);
  }

}
