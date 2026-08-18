package com.example.flight_booking.service;

import com.example.flight_booking.dto.FlightFilterRequestDto;
import com.example.flight_booking.dto.FlightFilterResponseDto;
import com.example.flight_booking.dto.FlightIataCodeRequestDto;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.FlightStatus;
import com.example.flight_booking.repository.AircraftRepository;
import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.repository.AirportRepository;
import com.example.flight_booking.repository.FlightRepository;
import java.time.LocalDateTime;
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

  public Flight createFlight(String flightNumber,
      Long departureAirportId,
      Long arrivalAirportId,
      Long aircraftId,
      Long airlineId,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      FlightStatus status) {
    Airport departureAirport = airportRepository.findById(departureAirportId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Departure airport not found with id " + departureAirportId));
    Airport arrivalAirport = airportRepository.findById(arrivalAirportId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Arrival airport not found with id " + arrivalAirportId));
    Aircraft aircraft = aircraftRepository.findById(aircraftId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Aircraft not found with id " + aircraftId));
    Airline airline = airlineRepository.findById(airlineId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + airlineId));

    Flight flight = new Flight();
    flight.setFlightNumber(flightNumber);
    flight.setDepartureAirport(departureAirport);
    flight.setArrivalAirport(arrivalAirport);
    flight.setDepartureTime(departureTime);
    flight.setArrivalTime(arrivalTime);
    flight.setAircraft(aircraft);
    flight.setAirline(airline);
    flight.setStatus(status);
    return flightRepository.save(flight);
  }

  public List<Flight> getAllFlights() {
    return flightRepository.findAll();
  }

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

  public Flight updateFlight(Long id,
      String flightNumber,
      Long departureAirportId,
      Long arrivalAirportId,
      Long aircraftId,
      Long airlineId,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      FlightStatus status) {
    Flight flight = getFlightEntityById(id);

    Airport departureAirport = airportRepository.findById(departureAirportId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Departure airport not found with id " + departureAirportId));
    Airport arrivalAirport = airportRepository.findById(arrivalAirportId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Arrival airport not found with id " + arrivalAirportId));
    Aircraft aircraft = aircraftRepository.findById(aircraftId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Aircraft not found with id " + aircraftId));
    Airline airline = airlineRepository.findById(airlineId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + airlineId));

    flight.setFlightNumber(flightNumber);
    flight.setDepartureAirport(departureAirport);
    flight.setArrivalAirport(arrivalAirport);
    flight.setDepartureTime(departureTime);
    flight.setArrivalTime(arrivalTime);
    flight.setAircraft(aircraft);
    flight.setAirline(airline);
    flight.setStatus(status);
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
