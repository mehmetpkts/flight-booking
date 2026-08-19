package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.dto.Airport.AirportUpdateRequestDto;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.repository.AirportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirportService {

  private final AirportRepository airportRepository;

  public AirportService(AirportRepository airportRepository) {
    this.airportRepository = airportRepository;
  }

  public Airport getAirportEntityById(Long id) {
    return airportRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airport not found with id " + id));
  }

  public AirportFilterResponseDto getAirportById(Long id){
    Airport airport = getAirportEntityById(id);

    AirportFilterResponseDto dto = new AirportFilterResponseDto();
    dto.setName(airport.getName());
    dto.setCity(airport.getCity());
    dto.setCountry(airport.getCountry());
    dto.setIataCode(airport.getIataCode());
    return dto;
  }

  public Airport createAirport(AirportCreateRequestDto create) {
    Airport airport = new Airport();

    airport.setName(create.getName());
    airport.setCountry(create.getCountry());
    airport.setCity(create.getCity());
    airport.setIataCode(create.getIataCode());

    return airportRepository.save(airport);
  }


  public Airport updateAirport(Long id, AirportUpdateRequestDto update) {
    Airport airport = getAirportEntityById(id);

    airport.setName(update.getName());
    airport.setCity(update.getCity());
    airport.setCountry(update.getCountry());
    airport.setIataCode(update.getIataCode());

    return airportRepository.save(airport);
  }

  public void deleteAirport(Long id) {
    Airport airport = getAirportEntityById(id);
    airportRepository.delete(airport);
  }

}
