package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.dto.Airline.AirlineUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.mapper.AirlineMapper;
import com.example.flight_booking.repository.AirlineRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirlineService {

  private final AirlineRepository airlineRepository;
  private final AirlineMapper airlineMapper;

  public AirlineService(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
    this.airlineRepository = airlineRepository;
    this.airlineMapper = airlineMapper;
  }


  public Airline getAirlineEntityById(Long id){
    return airlineRepository
            .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus
                    .NOT_FOUND, "Airline not defined. Id is: " + id));
  }

  public List<AirlineFilterResponseDto> getAllAirlines() {
    return airlineRepository.findAll().stream()
        .map(airlineMapper::toFilterResponseDto)
        .toList();
  }

  public AirlineFilterResponseDto getAirlineById(Long id) {
    Airline airline = getAirlineEntityById(id);
    return airlineMapper.toFilterResponseDto(airline);
  }

  public Airline createAirline(AirlineCreateRequestDto create){
    Airline airline = airlineMapper.toEntity(create);
    return airlineRepository.save(airline);
  }

  public Airline updateAirline(Long id, AirlineUpdateRequestDto update){
    Airline airline = getAirlineEntityById(id);
    airlineMapper.updateEntity(airline, update);
    return airlineRepository.save(airline);
  }

  public void deleteAirline(Long id){
    airlineRepository.delete(getAirlineEntityById(id));
  }


}
