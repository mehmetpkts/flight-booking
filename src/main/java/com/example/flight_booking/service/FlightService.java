package com.example.flight_booking.service;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.repository.AircraftRepository;
import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.repository.AirportRepository;
import com.example.flight_booking.repository.FlightRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

  private final FlightRepository flightRepository;
  private final AirportRepository airportRepository;
  private final AircraftRepository aircraftRepository;
  private final AirlineRepository airlineRepository;

  public FlightService(FlightRepository flightRepository,
      AirportRepository airportRepository,
      AircraftRepository aircraftRepository,
      AirlineRepository airlineRepository) {
    this.flightRepository = flightRepository;
    this.airportRepository = airportRepository;
    this.aircraftRepository = aircraftRepository;
    this.airlineRepository = airlineRepository;
  }





  public Flight createFlight(FlightCreateRequestDto create) {

    Flight flight = new Flight();

    flight.setFlightNumber(create.getFlightNumber());

    Airport departureAirport = airportRepository.findById(create.getDepartureAirportId())
            .orElseThrow(() -> new RuntimeException("departureAirport yok"));
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = airportRepository.findById(create.getArrivalAirportId())
            .orElseThrow(() -> new RuntimeException("arrival airport yok"));
    flight.setArrivalAirport(arrivalAirport);

    Aircraft aircraft = aircraftRepository.findById(create.getAircraftId())
            .orElseThrow(() -> new RuntimeException("aircraft id yok"));
    flight.setAircraft(aircraft);

    Airline airline = airlineRepository.findById(create.getAirlineId())
            .orElseThrow(() -> new RuntimeException("airline id yok"));
    flight.setAirline(airline);

    flight.setDepartureTime(create.getDepartureTime());
    flight.setArrivalTime(create.getArrivalTime());
    flight.setStatus(create.getStatus());

    return flightRepository.save(flight);
  }

//  public List<Flight> getAllFlights() {
//    return flightRepository.findAll();
//  }

  public FlightFilterResponseDto getFlightById(Long id) {
    Flight flight = getFlightEntityById(id);

    FlightFilterResponseDto dto = new FlightFilterResponseDto();
    dto.setFlightId(flight.getFlightId());
    dto.setFlightNumber(flight.getFlightNumber());
    dto.setDepartureAirportName(flight.getDepartureAirport().getName());
    dto.setDepartureAirportCity(flight.getDepartureAirport().getCity());
    dto.setArrivalAirportName(flight.getArrivalAirport().getName());
    dto.setArrivalAirportCity(flight.getArrivalAirport().getCity());
    dto.setAirlineName(flight.getAirline().getName());
    dto.setAirlineCountry(flight.getAirline().getCountry());

    return dto;
  }

  private Flight getFlightEntityById(Long id) {
    return flightRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + id));
  }

  public Flight updateFlight(Long id, FlightUpdateRequestDto update) {
    Flight flight = getFlightEntityById(id);

    flight.setFlightNumber(update.getFlightNumber());

    Airport departureAirport = airportRepository.findById(update.getDepartureAirportId())
            .orElseThrow(() -> new RuntimeException("departureAirport yok"));
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = airportRepository.findById(update.getArrivalAirportId())
            .orElseThrow(() -> new RuntimeException("arrival airport yok"));
    flight.setArrivalAirport(arrivalAirport);

    Aircraft aircraft = aircraftRepository.findById(update.getAircraftId())
            .orElseThrow(() -> new RuntimeException("aircraft id yok"));
    flight.setAircraft(aircraft);

    Airline airline = airlineRepository.findById(update.getAirlineId())
            .orElseThrow(() -> new RuntimeException("airline id yok"));
    flight.setAirline(airline);

    flight.setDepartureTime(update.getDepartureTime());
    flight.setArrivalTime(update.getArrivalTime());
    flight.setStatus(update.getStatus());

    return flightRepository.save(flight);
  }

  public void deleteFlight(Long id) {
    flightRepository.delete(getFlightEntityById(id));
  }

  public List<Flight> getByIataCode(FlightIataCodeRequestDto flightIataCodeRequestDto){
    List<Flight> flights = flightRepository.findByDepartureAirportIataCode(
            flightIataCodeRequestDto.getIataCode());

    return flights;
  }

  public List<Flight> getByArrivalAndDepartureCitiesAndStatus(FlightFilterRequestDto filterRequestDto) {
    List<Flight> flights = flightRepository.findByDepartureAirport_cityAndArrivalAirport_cityAndStatus(
        filterRequestDto.getDepartureCity(),
        filterRequestDto.getArrivalCity(),
        filterRequestDto.getFlightStatus());

    return flights;
  }

}
