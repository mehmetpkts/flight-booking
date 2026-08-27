package com.example.flight_booking.service;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.mapper.AircraftMapper;
import com.example.flight_booking.repository.AircraftRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AircraftService {

  private final AircraftRepository aircraftRepository;
  private final AirlineService airlineService;
  private final AircraftMapper aircraftMapper;

  public AircraftService(
      AircraftRepository aircraftRepository,
      AirlineService airlineService,
      AircraftMapper aircraftMapper) {
    this.aircraftRepository = aircraftRepository;
    this.airlineService = airlineService;
    this.aircraftMapper = aircraftMapper;
  }

  public Aircraft getAircraftEntityById(Long id){
    return aircraftRepository
            .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus
                    .NOT_FOUND, "aircraft id is not: " + id));
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineService.getAirlineEntityById(id);
  }

  public AircraftFilterResponseDto getAircraftById(Long id){
    Aircraft aircraft = getAircraftEntityById(id);
    return aircraftMapper.toFilterResponseDto(aircraft);
  }

  public Aircraft createAircraft(AircraftCreateRequestDto create) {
    Airline airline = getAirlineEntityById(create.getAirlineId());
    Aircraft aircraft = aircraftMapper.toEntity(create, airline);
    return aircraftRepository.save(aircraft);
  }


  public Aircraft updateAircraft(Long id, AircraftUpdateRequestDto update) {
    Airline airline = getAirlineEntityById(update.getAirlineId());
    Aircraft aircraft = getAircraftEntityById(id);
    aircraftMapper.updateEntity(aircraft, update, airline);
    return aircraftRepository.save(aircraft);
  }

  public void deleteAircraft(Long id) {
    aircraftRepository.delete(getAircraftEntityById(id));
  }

}
