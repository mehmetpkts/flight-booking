package com.example.flight_booking.service;

import com.example.flight_booking.dto.flight.*;
import com.example.flight_booking.entity.*;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.enums.FlightStatus;
import com.example.flight_booking.exception.BusinessRuleException;
import com.example.flight_booking.exception.ResourceNotFoundException;
import com.example.flight_booking.exception.ValidationException;
import com.example.flight_booking.mapper.FlightMapper;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.FlightRepository;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightService {

  private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
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
    logger.debug("Uçuş aranıyor. flightId={}", id);
    return flightRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Uçuş bulunamadı. flightId={}", id);
          return new ResourceNotFoundException("Flight", id);
        });
  }

  public Flight getFlightEntityByIdForUpdate(Long id) {
    logger.debug("Uçuş güncelleme için aranıyor. flightId={}", id);
    return flightRepository.findByFlightId(id)
        .orElseThrow(() -> {
          logger.warn("Uçuş bulunamadı. flightId={}", id);
          return new ResourceNotFoundException("Flight", id);
        });
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
    logger.info("Uçuş oluşturuluyor. flightNumber={}, airlineId={}, aircraftId={}, status={}",
        create.getFlightNumber(), create.getAirlineId(), create.getAircraftId(), create.getStatus());

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
    Flight savedFlight = flightRepository.save(flight);

    logger.info("Uçuş oluşturuldu. flightId={}, flightNumber={}",
        savedFlight.getFlightId(), savedFlight.getFlightNumber());
    return savedFlight;
  }

//  public List<Flight> getAllFlights() {
//    return flightRepository.findAll();
//  }

  public FlightFilterResponseDto getFlightById(Long id) {
    Flight flight = getFlightEntityById(id);
    return flightMapper.toFilterResponseDto(flight);
  }

  @Transactional
  public Flight updateFlight(Long id, FlightUpdateRequestDto update) {
    logger.info("Uçuş güncelleniyor. flightId={}, flightNumber={}, status={}",
        id, update.getFlightNumber(), update.getStatus());

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
    Flight savedFlight = flightRepository.save(flight);

    if (savedFlight.getStatus() == FlightStatus.CANCELLED) {
      cancelActiveBookingsOfFlight(savedFlight);
    }

    logger.info("Uçuş güncellendi. flightId={}, status={}",
        savedFlight.getFlightId(), savedFlight.getStatus());
    return savedFlight;
  }

  // uçuş iptal edilirse aktif rezervasyonlar cezasız iptal edilir
  private void cancelActiveBookingsOfFlight(Flight flight) {
    List<Booking> activeBookings = bookingRepository.findByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        NON_DELETABLE_BOOKING_STATUSES);

    logger.info("İptal edilen uçuşun aktif rezervasyonları iptal ediliyor. flightId={}, bookingCount={}",
        flight.getFlightId(), activeBookings.size());

    for (Booking booking : activeBookings) {
      booking.setStatus(BookingStatus.CANCELLED);
      booking.setCancellationPenaltyApplied(false);
      booking.setCancellationPenaltyAmount(BigDecimal.ZERO);
    }

    bookingRepository.saveAll(activeBookings);
  }

  private void validateFlightDetails(
      Long departureAirportId,
      Long arrivalAirportId,
      java.time.LocalDateTime departureTime,
      java.time.LocalDateTime arrivalTime) {
    if (Objects.equals(departureAirportId, arrivalAirportId)) {
      throw new BusinessRuleException("Departure and arrival airports must be different");
    }

    if (departureTime == null || arrivalTime == null) {
      throw new ValidationException("Departure time and arrival time are required");
    }

    if (!arrivalTime.isAfter(departureTime)) {
      throw new ValidationException("Arrival time must be after departure time");
    }
  }

  public void deleteFlight(Long id) {
    logger.info("Uçuş siliniyor. flightId={}", id);

    Flight flight = getFlightEntityById(id);
    long blockingBookingCount = bookingRepository.countByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        NON_DELETABLE_BOOKING_STATUSES);

    if (blockingBookingCount > 0) {
      logger.warn("Uçuş silinemedi; aktif rezervasyonlar var. flightId={}, bookingCount={}",
          id, blockingBookingCount);
      throw new BusinessRuleException(HttpStatus.CONFLICT,
          "Cannot delete flight with confirmed or checked-in bookings. flight id " + id);
    }

    flightRepository.delete(flight);
    logger.info("Uçuş silindi. flightId={}", id);
  }

  public List<Flight> getByIataCode(FlightIataCodeRequestDto flightIataCodeRequestDto){
    logger.debug("Kalkış IATA koduna göre uçuşlar filtreleniyor. iataCode={}",
        flightIataCodeRequestDto.getIataCode());
    List<Flight> flights = flightRepository.findByDepartureAirportIataCode(
        flightIataCodeRequestDto.getIataCode());
    logger.debug("Kalkış IATA koduna göre uçuşlar listelendi. resultCount={}", flights.size());
    return flights;
  }

  public List<Flight> getByArrivalAndDepartureCitiesAndStatus(FlightFilterRequestDto filterRequestDto) {
    logger.debug("Uçuşlar filtreleniyor. departureCity={}, arrivalCity={}, status={}",
        filterRequestDto.getDepartureCity(), filterRequestDto.getArrivalCity(),
        filterRequestDto.getFlightStatus());
    List<Flight> flights = flightRepository.findByDepartureAirport_cityAndArrivalAirport_cityAndStatus(
        filterRequestDto.getDepartureCity(),
        filterRequestDto.getArrivalCity(),
        filterRequestDto.getFlightStatus());
    logger.debug("Uçuşlar filtrelendi. resultCount={}", flights.size());
    return flights;
  }

}
