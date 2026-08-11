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

  public void createAirport(Airport airport) {
    airportRepository.save(airport);
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

  public void updateAirport(Airport airport) {
    airportRepository.save(airport);
  }

  // silme

  public void deleteAirport(Long id) {
    airportRepository.deleteById(id);
  }

}
