package com.example.flight_booking.service;

import com.example.flight_booking.dto.Airline.AirlineCreateRequestDto;
import com.example.flight_booking.dto.Airline.AirlineFilterResponseDto;
import com.example.flight_booking.dto.Airline.AirlineUpdateRequestDto;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.repository.AirlineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirlineService {

  private final AirlineRepository airlineRepository;

  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }


  private Airline getAirlineEntityById(Long id){
    return airlineRepository
            .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus
                    .NOT_FOUND, "Airline not defined. Id is: " + id));
  }

  public AirlineFilterResponseDto getAirlineById(Long id) {
    Airline airline = getAirlineEntityById(id);

    AirlineFilterResponseDto dto = new AirlineFilterResponseDto();
    dto.setName(airline.getName());
    dto.setCountry(airline.getCountry());
    dto.setIataCode(airline.getIataCode());

    return dto;
  }

  public Airline createAirline(AirlineCreateRequestDto create){
    Airline airline = new Airline();

    airline.setCountry(create.getCountry());
    airline.setIataCode(create.getIataCode());
    airline.setName(create.getName());

    return airlineRepository.save(airline);
  }

  public Airline updateAirline(Long id, AirlineUpdateRequestDto update){
    Airline airline = getAirlineEntityById(id);

    airline.setName(update.getName());
    airline.setIataCode(update.getIataCode());
    airline.setCountry(update.getCountry());

    return airlineRepository.save(airline);
  }

  public void deleteAirline(Long id){
    airlineRepository.delete(getAirlineEntityById(id));
  }


}
