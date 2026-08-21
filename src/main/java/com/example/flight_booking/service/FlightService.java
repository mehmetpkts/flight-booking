package com.example.flight_booking.service;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.Aircraft;
import com.example.flight_booking.entity.Airline;
import com.example.flight_booking.entity.Airport;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.repository.AircraftRepository;
import com.example.flight_booking.repository.AirlineRepository;
import com.example.flight_booking.repository.AirportRepository;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.FlightRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

  private static final Set<BookingStatus> NON_DELETABLE_BOOKING_STATUSES = EnumSet.of(
      BookingStatus.CONFIRMED);

  private final FlightRepository flightRepository;
  private final AirportRepository airportRepository;
  private final AircraftRepository aircraftRepository;
  private final AirlineRepository airlineRepository;
  private final BookingRepository bookingRepository;

  public FlightService(FlightRepository flightRepository,
      AirportRepository airportRepository,
      AircraftRepository aircraftRepository,
      AirlineRepository airlineRepository,
      BookingRepository bookingRepository) {
    this.flightRepository = flightRepository;
    this.airportRepository = airportRepository;
    this.aircraftRepository = aircraftRepository;
    this.airlineRepository = airlineRepository;
    this.bookingRepository = bookingRepository;
  }

  private Flight getFlightEntityById(Long id) {
    return flightRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Flight not found with id " + id));
  }

  private Airport getAirportEntityById(Long id, String airportType) {
    return airportRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            airportType + " airport not found with id " + id));
  }

  private Aircraft getAircraftEntityById(Long id) {
    return aircraftRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Aircraft not found with id " + id));
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Airline not found with id " + id));
  }


  public Flight createFlight(FlightCreateRequestDto create) {

    Flight flight = new Flight();

    flight.setFlightNumber(create.getFlightNumber());

    Airport departureAirport = getAirportEntityById(create.getDepartureAirportId(), "Departure");
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = getAirportEntityById(create.getArrivalAirportId(), "Arrival");
    flight.setArrivalAirport(arrivalAirport);

    Aircraft aircraft = getAircraftEntityById(create.getAircraftId());
    flight.setAircraft(aircraft);

    Airline airline = getAirlineEntityById(create.getAirlineId());
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

  public Flight updateFlight(Long id, FlightUpdateRequestDto update) {
    Flight flight = getFlightEntityById(id);

    flight.setFlightNumber(update.getFlightNumber());

    Airport departureAirport = getAirportEntityById(update.getDepartureAirportId(), "Departure");
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = getAirportEntityById(update.getArrivalAirportId(), "Arrival");
    flight.setArrivalAirport(arrivalAirport);

    Aircraft aircraft = getAircraftEntityById(update.getAircraftId());
    flight.setAircraft(aircraft);

    Airline airline = getAirlineEntityById(update.getAirlineId());
    flight.setAirline(airline);

    flight.setDepartureTime(update.getDepartureTime());
    flight.setArrivalTime(update.getArrivalTime());
    flight.setStatus(update.getStatus());

    return flightRepository.save(flight);
  }

  public void deleteFlight(Long id) {
    Flight flight = getFlightEntityById(id);
    long blockingBookingCount = bookingRepository.countByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        NON_DELETABLE_BOOKING_STATUSES);

    if (blockingBookingCount > 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Cannot delete flight with confirmed bookings. flight id " + id);
    }

    flightRepository.delete(flight);
  }

  public List<Flight> getByIataCode(FlightIataCodeRequestDto flightIataCodeRequestDto){
    return flightRepository.findByDepartureAirportIataCode(
        flightIataCodeRequestDto.getIataCode());
  }

  public List<Flight> getByArrivalAndDepartureCitiesAndStatus(FlightFilterRequestDto filterRequestDto) {
    return flightRepository.findByDepartureAirport_cityAndArrivalAirport_cityAndStatus(
        filterRequestDto.getDepartureCity(),
        filterRequestDto.getArrivalCity(),
        filterRequestDto.getFlightStatus());
  }

}
