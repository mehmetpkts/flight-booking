package com.example.flight_booking.service;

import com.example.flight_booking.repository.AirportRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.flight_booking.entity.Airport;

@Service
public class AirportService {

  private final AirportRepository airportRepository;

  public AirportService(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  // oluşturma - kaydetme

  public Airport createAirport(String name, String city, String country, String iataCode) {
    Airport airport = new Airport();
    airport.setName(name);
    airport.setCity(city);
    airport.setCountry(country);
    airport.setIataCode(iataCode);
    return airportRepository.save(airport);
  }

  // okuma - listeleme

  public List<Airport> getAllAirports() {
    return airportRepository.findAll();
  }

  // id okuma

  public Airport getAirportById(Long id) {
    return airportRepository.findById(id).orElse(null);
  }

  // güncelleme

  public Airport updateAirport(Long id, String name, String city, String country, String iataCode) {
    Airport airport = airportRepository.findById(id).orElse(null);
    if (airport != null) {
      airport.setName(name);
      airport.setCity(city);
      airport.setCountry(country);
      airport.setIataCode(iataCode);
      return airportRepository.save(airport);
    }
    return null;
  }

  // silme

  public void deleteAirport(Long id) {
    airportRepository.deleteById(id);
  }

}
