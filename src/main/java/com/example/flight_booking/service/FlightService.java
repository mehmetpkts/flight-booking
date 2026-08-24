package com.example.flight_booking.service;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.*;
import com.example.flight_booking.enums.BookingStatus;

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
  private final AirportService airportService;
  private final AircraftService aircraftService;
  private final AirlineService airlineService;
  private final BookingService bookingService;

  public FlightService(FlightRepository flightRepository,
      AirportService airportService,
      AircraftService aircraftService,
      AirlineService airlineService,
      BookingService bookingService) {
    this.flightRepository = flightRepository;
    this.airportService = airportService;
    this.aircraftService = aircraftService;
    this.airlineService = airlineService;
    this.bookingService = bookingService;
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

    Flight flight = new Flight();

    flight.setFlightNumber(create.getFlightNumber());

    Airport departureAirport = getAirportEntityById(create.getDepartureAirportId());
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = getAirportEntityById(create.getArrivalAirportId());
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

    Airport departureAirport = getAirportEntityById(update.getDepartureAirportId());
    flight.setDepartureAirport(departureAirport);

    Airport arrivalAirport = getAirportEntityById(update.getArrivalAirportId());
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
    long blockingBookingCount = bookingService.countByFlight_FlightIdAndStatusIn(
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
