package com.example.flight_booking.service;

import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.repository.AirlineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AirlineService {

  private final AirlineRepository airlineRepository;

  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  public List<Airline> getAllAirlines() {
    return airlineRepository.findAll();
  }

  public Airline getAirlineById(Long id) {
    return airlineRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + id));
  }

  public Airline createAirline(String name, String iataCode, String country) {
    Airline airline = new Airline();
    airline.setName(name);
    airline.setIataCode(iataCode);
    airline.setCountry(country);
    return airlineRepository.save(airline);
  }

  public Airline updateAirline(Long id, String name, String iataCode, String country) {
    Airline airline = getAirlineById(id);
    airline.setName(name);
    airline.setIataCode(iataCode);
    airline.setCountry(country);
    return airlineRepository.save(airline);
  }

  public void deleteAirline(Long id) {
    Airline airline = getAirlineById(id);
    airlineRepository.delete(airline);
  }
}
