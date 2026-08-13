package com.example.flight_booking.service;

import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.repository.AirportRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirportService {

  private final AirportRepository airportRepository;

  public AirportService(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  public Airport createAirport(String name, String city, String country, String iataCode) {
    Airport airport = new Airport();
    airport.setName(name);
    airport.setCity(city);
    airport.setCountry(country);
    airport.setIataCode(iataCode);
    return airportRepository.save(airport);
  }

  public List<Airport> getAllAirports() {
    return airportRepository.findAll();
  }

  public Airport getAirportById(Long id) {
    return airportRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airport not found with id " + id));
  }

  public Airport updateAirport(Long id, String name, String city, String country, String iataCode) {
    Airport airport = getAirportById(id);
    airport.setName(name);
    airport.setCity(city);
    airport.setCountry(country);
    airport.setIataCode(iataCode);
    return airportRepository.save(airport);
  }

  public void deleteAirport(Long id) {
    Airport airport = getAirportById(id);
    airportRepository.delete(airport);
  }

}
