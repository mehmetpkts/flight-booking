package com.example.flight_booking.service;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.dto.Booking.BookingUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.util.PnrGeneratorUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

  private static final BigDecimal CANCELLATION_PENALTY_FEE = new BigDecimal("250.00");
  private static final int MAX_PNR_GENERATION_ATTEMPTS = 20;
  private static final Set<BookingStatus> SEAT_OCCUPYING_STATUSES = EnumSet.of(
      BookingStatus.CHECKED_IN,
      BookingStatus.CONFIRMED);

  private final BookingRepository bookingRepository;
  private final PassengerService passengerService;
  private final FlightService flightService;

  public BookingService(BookingRepository bookingRepository,
      PassengerService passengerService,
      FlightService flightService) {
    this.bookingRepository = bookingRepository;
    this.passengerService = passengerService;
    this.flightService = flightService;
  }

  public Booking getBookingEntityById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  private Passenger getPassengerEntityById(Long id) {
    return passengerService.getPassengerEntityById(id);
  }


  public BookingFilterResponseDto getBookingById(Long id){
    Booking booking = getBookingEntityById(id);

    BookingFilterResponseDto dto = new BookingFilterResponseDto();

    dto.setFlight(booking.getFlight());
    dto.setBookingDate(booking.getBookingDate());
    dto.setPassenger(booking.getPassenger());
    dto.setPnr(booking.getPnr());
    dto.setStatus(booking.getStatus());
    dto.setCancellationPenaltyApplied(booking.getCancellationPenaltyApplied());
    dto.setCancellationPenaltyAmount(booking.getCancellationPenaltyAmount());

    return dto;
  }


  @Transactional
  public Booking createBooking(BookingCreateRequestDto create) {

    Booking booking = new Booking();
    Passenger passenger = getPassengerEntityById(create.getPassengerId());
    Flight flight = getFlightEntityByIdForUpdate(create.getFlightId());

    validateFlightDepartureTime(flight);
    validatePassengerHasNoBookingForFlight(passenger.getPassengerId(), flight.getFlightId());
    validateFlightCapacity(flight, create.getStatus(), false);

    booking.setBookingDate(create.getBookingDate());
    booking.setPnr(generateUniquePnr());
    booking.setStatus(create.getStatus());
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    applyCancellationPenalty(booking, create.getStatus(), flight);


    return bookingRepository.save(booking);
  }


  @Transactional
  public Booking updateBooking(Long id, BookingUpdateRequestDto update) {
    Booking booking = getBookingEntityById(id);
    Passenger passenger = getPassengerEntityById(update.getPassengerId());
    Flight flight = getFlightEntityByIdForUpdate(update.getFlightId());
    boolean alreadyOccupyingSameFlightSeat = booking.getFlight().getFlightId().equals(update.getFlightId())
        && SEAT_OCCUPYING_STATUSES.contains(booking.getStatus());

    validateFlightDepartureTime(flight);
    validatePassengerHasNoOtherBookingForFlight(passenger.getPassengerId(), flight.getFlightId(), id);
    validatePnrIsUniqueForOtherBooking(update.getPnr(), id);
    validateFlightCapacity(flight, update.getStatus(), alreadyOccupyingSameFlightSeat);

    booking.setBookingDate(update.getBookingDate());
    booking.setPnr(update.getPnr());
    booking.setStatus(update.getStatus());
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    applyCancellationPenalty(booking, update.getStatus(), flight);


    return bookingRepository.save(booking);
  }

  public void deleteBooking(Long id) {
    Booking booking = getBookingEntityById(id);
    bookingRepository.delete(booking);
  }

  private Flight getFlightEntityByIdForUpdate(Long flightId) {
    return flightService.getFlightEntityByIdForUpdate(flightId);
  }

  private void validateFlightCapacity(Flight flight, BookingStatus targetStatus,
      boolean alreadyOccupyingSameFlightSeat) {
    if (!SEAT_OCCUPYING_STATUSES.contains(targetStatus)) {
      return;
    }

    long occupiedSeatCount = bookingRepository.countByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        SEAT_OCCUPYING_STATUSES);

    if (alreadyOccupyingSameFlightSeat) {
      occupiedSeatCount--;
    }

    if (occupiedSeatCount >= flight.getAircraft().getCapacity()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Flight capacity exceeded for flight id " + flight.getFlightId());
    }
  }

  private void validatePassengerHasNoBookingForFlight(Long passengerId, Long flightId) {
    if (bookingRepository.existsByPassenger_PassengerIdAndFlight_FlightId(passengerId, flightId)) {
      throw duplicateBookingException(passengerId, flightId);
    }
  }

  private void validatePassengerHasNoOtherBookingForFlight(Long passengerId, Long flightId,
      Long bookingId) {
    if (bookingRepository.existsByPassenger_PassengerIdAndFlight_FlightIdAndBookingIdNot(
        passengerId, flightId, bookingId)) {
      throw duplicateBookingException(passengerId, flightId);
    }
  }

  private ResponseStatusException duplicateBookingException(Long passengerId, Long flightId) {
    return new ResponseStatusException(HttpStatus.CONFLICT,
        "Passenger id " + passengerId + " already has a booking for flight id " + flightId);
  }

  private void validatePnrIsUniqueForOtherBooking(String pnr, Long bookingId) {
    if (bookingRepository.existsByPnrAndBookingIdNot(pnr, bookingId)) {
      throw duplicatePnrException(pnr);
    }
  }

  private String generateUniquePnr() {
    for (int attempt = 0; attempt < MAX_PNR_GENERATION_ATTEMPTS; attempt++) {
      String generatedPnr = PnrGeneratorUtil.generateRandomPnr();
      if (!bookingRepository.existsByPnr(generatedPnr)) {
        return generatedPnr;
      }
    }

    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
        "Could not generate a unique PNR");
  }

  private ResponseStatusException duplicatePnrException(String pnr) {
    return new ResponseStatusException(HttpStatus.CONFLICT,
        "Booking already exists with pnr " + pnr);
  }

  private void validateFlightDepartureTime(Flight flight) {
    if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Cannot create booking for a flight that has already departed. flight id " + flight.getFlightId());
    }
  }

  private void applyCancellationPenalty(Booking booking, BookingStatus targetStatus, Flight flight) {
    boolean isCancelled = targetStatus == BookingStatus.CANCELLED;
    boolean isWithin24Hours = !flight.getDepartureTime().isAfter(LocalDateTime.now().plusHours(24));
    boolean shouldApplyPenalty = isCancelled && isWithin24Hours;

    booking.setCancellationPenaltyApplied(shouldApplyPenalty);
    booking.setCancellationPenaltyAmount(
        shouldApplyPenalty ? CANCELLATION_PENALTY_FEE : BigDecimal.ZERO);
  }

}
