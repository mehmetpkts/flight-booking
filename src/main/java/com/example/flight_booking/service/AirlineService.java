package com.example.flight_booking.service;

import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.entity.Airline;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AirlineService {

  // bu tanım sayesinde sınıf içinde yazılan her metot veri tabanı üzerinde işlem
  // yapabilme yeteneği kazanır.
  private final AirlineRepository airlineRepository;

  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  // oluşturma - kaydetme

  public void createAirline(Airline airline) {
    airlineRepository.save(airline);
  }

  // okuma - listeleme

  public List<Airline> getAllAirlines() {
    return airlineRepository.findAll();
  }

  // id okuma

  public Airline getAirlineById(Long id) {
    return airlineRepository.findById(id).orElse(null);
  }

  // güncelleme

  public void updateAirline(Airline airline) {
    airlineRepository.save(airline);
  }

  // silme

  public void deleteAirline(Long id) {
    airlineRepository.deleteById(id);
  }

}
