package com.example.flight_booking.service;

import com.example.flight_booking.repository.FlightRepository;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import java.time.LocalDateTime;

import com.example.flight_booking.enums.FlightStatus;
import java.util.List;
import java.lang.String;

import com.example.flight_booking.entity.Flight;

import org.springframework.stereotype.Service;

@Service
public class FlightService {

  // oluşturma kaydetme:

  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  // oluşturma kaydetme:

  public Flight createFlight(String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
      Aircraft aircraft, Airline airline, FlightStatus status) {
    Flight flight = new Flight();
    flight.setFlightNumber(flightNumber);
    flight.setDepartureTime(departureTime);
    flight.setArrivalTime(arrivalTime);
    flight.setAircraft(aircraft);
    flight.setAirline(airline);
    flight.setStatus(status);
    return flightRepository.save(flight);
  }

  // okuma - listeleme:

  public List<Flight> getAllFlights() {
    return flightRepository.findAll();
  }

  // id okuma:

  public Flight getFlightById(Long id) {
    return flightRepository.findById(id).orElse(null);
  }

  // güncelleme:

  public Flight updateFlight(Long id, String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
      Aircraft aircraft, Airline airline, FlightStatus status) {
    Flight flight = flightRepository.findById(id).orElse(null);
    if (flight != null) {
      flight.setFlightNumber(flightNumber);
      flight.setDepartureTime(departureTime);
      flight.setArrivalTime(arrivalTime);
      flight.setAircraft(aircraft);
      flight.setAirline(airline);
      flight.setStatus(status);
      return flightRepository.save(flight);
    }
    return null;
  }

  // silme:

  public void deleteFlight(Long id) {
    flightRepository.deleteById(id);
  }

  // iataCode ile uçuşları listeleme:

  public List<Flight> getFlightsByIataCode(String iataCode) {
    return flightRepository.findByDepartureAirport_iataCodeFlights(iataCode);
  }

}
