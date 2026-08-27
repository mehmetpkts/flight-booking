package com.example.flight_booking.service;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.*;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.mapper.FlightMapper;
import com.example.flight_booking.repository.BookingRepository;

import com.example.flight_booking.repository.FlightRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

  private static final Set<BookingStatus> NON_DELETABLE_BOOKING_STATUSES = EnumSet.of(
      BookingStatus.CONFIRMED,
      BookingStatus.CHECKED_IN);

  private final FlightRepository flightRepository;
  private final BookingRepository bookingRepository;
  private final AirportService airportService;
  private final AircraftService aircraftService;
  private final AirlineService airlineService;
  private final FlightMapper flightMapper;

  public FlightService(FlightRepository flightRepository,
      BookingRepository bookingRepository,
      AirportService airportService,
      AircraftService aircraftService,
      AirlineService airlineService,
      FlightMapper flightMapper) {
    this.flightRepository = flightRepository;
    this.bookingRepository = bookingRepository;
    this.airportService = airportService;
    this.aircraftService = aircraftService;
    this.airlineService = airlineService;
    this.flightMapper = flightMapper;
  }

  public Flight getFlightEntityById(Long id) {
    return flightRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Flight not found with id " + id));
  }

  public Flight getFlightEntityByIdForUpdate(Long id) {
    return flightRepository.findByFlightId(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + id));
  }

  private Airport getAirportEntityById(Long id) {
    return airportService.getAirportEntityById(id);
  }

  private Aircraft getAircraftEntityById(Long id) {
    return aircraftService.getAircraftEntityById(id);
  }

  private Airline getAirlineEntityById(Long id) {
    return airlineService.getAirlineEntityById(id);
  }


  public Flight createFlight(FlightCreateRequestDto create) {
    validateFlightDetails(
        create.getDepartureAirportId(),
        create.getArrivalAirportId(),
        create.getDepartureTime(),
        create.getArrivalTime());
    Airport departureAirport = getAirportEntityById(create.getDepartureAirportId());
    Airport arrivalAirport = getAirportEntityById(create.getArrivalAirportId());
    Aircraft aircraft = getAircraftEntityById(create.getAircraftId());
    Airline airline = getAirlineEntityById(create.getAirlineId());
    Flight flight = flightMapper.toEntity(create, departureAirport, arrivalAirport, aircraft, airline);
    return flightRepository.save(flight);
  }

//  public List<Flight> getAllFlights() {
//    return flightRepository.findAll();
//  }

  public FlightFilterResponseDto getFlightById(Long id) {
    Flight flight = getFlightEntityById(id);
    return flightMapper.toFilterResponseDto(flight);
  }

  public Flight updateFlight(Long id, FlightUpdateRequestDto update) {
    validateFlightDetails(
        update.getDepartureAirportId(),
        update.getArrivalAirportId(),
        update.getDepartureTime(),
        update.getArrivalTime());
    Flight flight = getFlightEntityById(id);
    Airport departureAirport = getAirportEntityById(update.getDepartureAirportId());
    Airport arrivalAirport = getAirportEntityById(update.getArrivalAirportId());
    Aircraft aircraft = getAircraftEntityById(update.getAircraftId());
    Airline airline = getAirlineEntityById(update.getAirlineId());
    flightMapper.updateEntity(flight, update, departureAirport, arrivalAirport, aircraft, airline);
    return flightRepository.save(flight);
  }

  private void validateFlightDetails(
      Long departureAirportId,
      Long arrivalAirportId,
      java.time.LocalDateTime departureTime,
      java.time.LocalDateTime arrivalTime) {
    if (Objects.equals(departureAirportId, arrivalAirportId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Departure and arrival airports must be different");
    }

    if (departureTime == null || arrivalTime == null || !arrivalTime.isAfter(departureTime)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Arrival time must be after departure time");
    }
  }

  public void deleteFlight(Long id) {
    Flight flight = getFlightEntityById(id);
    long blockingBookingCount = bookingRepository.countByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        NON_DELETABLE_BOOKING_STATUSES);

    if (blockingBookingCount > 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Cannot delete flight with confirmed or checked-in bookings. flight id " + id);
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
