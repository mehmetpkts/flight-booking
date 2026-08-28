package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airport.AirportCreateRequestDto;
import com.example.flight_booking.dto.Airport.AirportFilterResponseDto;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.mapper.AirportMapper;
import com.example.flight_booking.repository.AirportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirportService {

  private final AirportRepository airportRepository;
  private final AirportMapper airportMapper;

  public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
    this.airportRepository = airportRepository;
    this.airportMapper = airportMapper;
  }

  public Airport getAirportEntityById(Long id) {
    return airportRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airport not found with id " + id));
  }

  public AirportFilterResponseDto getAirportById(Long id){
    Airport airport = getAirportEntityById(id);
    return airportMapper.toFilterResponseDto(airport);
  }

  public Airport createAirport(AirportCreateRequestDto create) {
    Airport airport = airportMapper.toEntity(create);
    return airportRepository.save(airport);
  }

  public void deleteAirport(Long id) {
    Airport airport = getAirportEntityById(id);
    airportRepository.delete(airport);
  }

}
