package com.example.flight_booking.service;

import com.example.flight_booking.dto.Aircraft.AircraftCreateRequestDto;
import com.example.flight_booking.dto.Aircraft.AircraftFilterResponseDto;
import com.example.flight_booking.dto.Aircraft.AircraftUpdateRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.repository.AircraftRepository;
import com.example.flight_booking.repository.AirlineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AircraftService {

  private final AircraftRepository aircraftRepository;
  private final AirlineRepository airlineRepository;

  public AircraftService(AircraftRepository aircraftRepository, AirlineRepository airlineRepository) {
    this.aircraftRepository = aircraftRepository;
    this.airlineRepository = airlineRepository;
  }

  private Aircraft getAircraftEntityById(Long id){
    return aircraftRepository
            .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus
                    .NOT_FOUND, "aircraft id is not: " + id));
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "airline id is no: " + id));
  }

  public AircraftFilterResponseDto getAircraftById(Long id){
    Aircraft aircraft = getAircraftEntityById(id);

    AircraftFilterResponseDto dto = new AircraftFilterResponseDto();
    dto.setAirlineId(aircraft.getAirline().getAirlineId());
    dto.setAirlineName(aircraft.getAirline().getName());
    dto.setCapacity(aircraft.getCapacity());
    dto.setManufacturer(aircraft.getManufacturer());
    dto.setModel(aircraft.getModel());
    dto.setStatus(aircraft.getStatus().name());

    return dto;
  }

  public Aircraft createAircraft(AircraftCreateRequestDto create) {
    Airline airline = getAirlineEntityById(create.getAirlineId());

    Aircraft aircraft = new Aircraft();
    aircraft.setModel(create.getModel());
    aircraft.setManufacturer(create.getManufacturer());
    aircraft.setCapacity(create.getCapacity());
    aircraft.setStatus(create.getStatus());
    aircraft.setAirline(airline);

    return aircraftRepository.save(aircraft);
  }


  public Aircraft updateAircraft(Long id, AircraftUpdateRequestDto update) {
    Aircraft aircraft = getAircraftEntityById(id);

    aircraft.setModel(update.getModel());
    aircraft.setManufacturer(update.getManufacturer());
    aircraft.setCapacity(update.getCapacity());
    aircraft.setStatus(update.getStatus());
    return aircraftRepository.save(aircraft);
  }

  public void deleteAircraft(Long id) {
    aircraftRepository.delete(getAircraftEntityById(id));
  }

}
