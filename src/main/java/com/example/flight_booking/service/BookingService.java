package com.example.flight_booking.service;

import com.example.flight_booking.dto.Booking.BookingCreateRequestDto;
import com.example.flight_booking.dto.Booking.BookingFilterResponseDto;
import com.example.flight_booking.dto.Booking.BookingUpdateRequestDto;
import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.exception.BusinessRuleException;
import com.example.flight_booking.exception.DuplicateResourceException;
import com.example.flight_booking.exception.ResourceNotFoundException;
import com.example.flight_booking.mapper.BookingMapper;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.util.PnrGeneratorUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

  private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
  private static final BigDecimal CANCELLATION_PENALTY_FEE = new BigDecimal("250.00");
  private static final int MAX_PNR_GENERATION_ATTEMPTS = 20;
  private static final Set<BookingStatus> UPDATABLE_BOOKING_STATUSES = EnumSet.of(
      BookingStatus.CANCELLED,
      BookingStatus.CHECKED_IN);
  private static final Set<BookingStatus> SEAT_OCCUPYING_STATUSES = EnumSet.of(
      BookingStatus.CHECKED_IN,
      BookingStatus.CONFIRMED);

  private final BookingRepository bookingRepository;
  private final PassengerService passengerService;
  private final FlightService flightService;
  private final BookingMapper bookingMapper;

  public BookingService(BookingRepository bookingRepository,
      PassengerService passengerService,
      FlightService flightService,
      BookingMapper bookingMapper) {
    this.bookingRepository = bookingRepository;
    this.passengerService = passengerService;
    this.flightService = flightService;
    this.bookingMapper = bookingMapper;
  }

  public Booking getBookingEntityById(Long id) {
    logger.debug("Booking aranıyor. bookingId={}", id);
    return bookingRepository.findById(id)
        .orElseThrow(() -> {
          logger.warn("Booking bulunamadı. bookingId={}", id);
          return new ResourceNotFoundException("Booking", id);
        });
  }

  private Passenger getPassengerEntityById(Long id) {
    return passengerService.getPassengerEntityById(id);
  }

  private Flight getFlightEntityByIdForUpdate(Long flightId) {
    return flightService.getFlightEntityByIdForUpdate(flightId);
  }

  public BookingFilterResponseDto getBookingById(Long id) {
    Booking booking = getBookingEntityById(id);
    BookingStatus effectiveStatus = synchronizeBookingStatus(booking);
    return toBookingFilterResponse(booking, effectiveStatus);
  }

  private BookingStatus synchronizeBookingStatus(Booking booking) {
    BookingStatus effectiveStatus = resolveEffectiveStatus(booking.getStatus(), booking.getFlight());

    if (booking.getStatus() != effectiveStatus) {
      booking.setStatus(effectiveStatus);
      bookingRepository.save(booking);
    }

    return effectiveStatus;
  }

  private BookingFilterResponseDto toBookingFilterResponse(Booking booking, BookingStatus effectiveStatus) {
    return bookingMapper.toFilterResponseDto(booking, effectiveStatus);
  }


  // create için oluşturduğumuz fonksiyonların kullanılması
  @Transactional
  public Booking createBooking(BookingCreateRequestDto create) {
    logger.info("Booking oluşturuluyor. passengerId={}, flightId={}, status={}",
        create.getPassengerId(), create.getFlightId(), create.getStatus());

    Passenger passenger = getPassengerEntityById(create.getPassengerId());
    Flight flight = getFlightEntityByIdForUpdate(create.getFlightId());

    validateFlightDepartureTime(flight);
    validatePassengerHasNoBookingForFlight(passenger.getPassengerId(), flight.getFlightId());
    BookingStatus effectiveStatus = resolveEffectiveStatus(create.getStatus(), flight);

    validateFlightCapacity(flight, effectiveStatus);

    Booking booking = bookingMapper.toEntity(
        create,
        passenger,
        flight,
        effectiveStatus,
        generateUniquePnr());
    applyCancellationPenalty(booking, effectiveStatus, flight);

    try {
      Booking savedBooking = bookingRepository.save(booking);
      logger.info("Booking oluşturuldu. bookingId={}, passengerId={}, flightId={}, status={}",
          savedBooking.getBookingId(), passenger.getPassengerId(), flight.getFlightId(),
          savedBooking.getStatus());
      return savedBooking;
    } catch (DataIntegrityViolationException ex) {
      throw new DuplicateResourceException("Booking already exists for this passenger and flight.");
    }
  }

  // update için oluşturduğumuz kuralların kullanılması
  @Transactional
  public Booking updateBookingStatus(Long id, BookingUpdateRequestDto update) {
    BookingStatus targetStatus = update.getStatus();
    logger.info("Booking durumu güncelleniyor. bookingId={}, targetStatus={}", id, targetStatus);
    validateUpdatableStatus(targetStatus);

    Booking booking = getBookingEntityById(id);
    Flight flight = getFlightEntityByIdForUpdate(booking.getFlight().getFlightId());
    BookingStatus currentStatus = booking.getStatus();

    if (currentStatus == targetStatus) {
      logger.info("Booking durumu zaten güncel. bookingId={}, status={}", id, currentStatus);
      return booking;
    }


    validateStatusTransition(currentStatus, targetStatus);
    validateFlightDepartureTimeForStatusUpdate(flight);

    if (targetStatus == BookingStatus.CHECKED_IN) {
      validateCheckInWindow(flight);
    }

    booking.setStatus(targetStatus);
    applyCancellationPenalty(booking, targetStatus, flight);

    try {
      Booking savedBooking = bookingRepository.save(booking);
      logger.info("Booking durumu güncellendi. bookingId={}, status={}",
          savedBooking.getBookingId(), savedBooking.getStatus());
      return savedBooking;
    } catch (DataIntegrityViolationException ex) {
      throw new DuplicateResourceException("Booking update failed due to a data conflict.");
    }
  }
  public void deleteBooking(Long id) {
    logger.info("Booking siliniyor. bookingId={}", id);

    Booking booking = getBookingEntityById(id);
    bookingRepository.delete(booking);

    logger.info("Booking silindi. bookingId={}", id);
  }
  // target statüye ait olan uçuşları topla ve kapasiteye bak
  private void validateFlightCapacity(Flight flight, BookingStatus targetStatus) {
    if (!SEAT_OCCUPYING_STATUSES.contains(targetStatus)) {
      return;
    }

    long occupiedSeatCount = bookingRepository.countByFlight_FlightIdAndStatusIn(
        flight.getFlightId(),
        SEAT_OCCUPYING_STATUSES);

    if (occupiedSeatCount >= flight.getAircraft().getCapacity()) {
      throw new BusinessRuleException(
          "Flight capacity exceeded for flight id " + flight.getFlightId());
    }
  }

  // aynı uçuşa birden fazla aynı kişi rezervasyon yapamaz
  private void validatePassengerHasNoBookingForFlight(Long passengerId, Long flightId) {
    if (bookingRepository.existsByPassenger_PassengerIdAndFlight_FlightId(passengerId, flightId)) {
      throw new DuplicateResourceException(
          "Passenger id " + passengerId + " already has a booking for flight id " + flightId);
    }
  }

  // eşsiz pnr değeri ve 20 farklı değere kadar üretme sınırı
  private String generateUniquePnr() {
    for (int attempt = 0; attempt < MAX_PNR_GENERATION_ATTEMPTS; attempt++) {
      String generatedPnr = PnrGeneratorUtil.generateRandomPnr();
      if (!bookingRepository.existsByPnr(generatedPnr)) {
        return generatedPnr;
      }
    }

    throw new BusinessRuleException(HttpStatus.INTERNAL_SERVER_ERROR,
        "Could not generate a unique PNR");
  }

  // uçuşun saat kontrolü(önce mi?)
  private void validateFlightDepartureTime(Flight flight) {
    if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
      throw new BusinessRuleException(
          "Cannot create booking for a flight that has already departed. flight id " + flight.getFlightId());
    }
  }

  // uçuşun saat kontrolü(sonra mı?) - durum değişikliğinde vs...
  private void validateFlightDepartureTimeForStatusUpdate(Flight flight) {
    if (!flight.getDepartureTime().isAfter(LocalDateTime.now())) {
      throw new BusinessRuleException(
          "Cannot update booking status after departure. flight id " + flight.getFlightId());
    }
  }

  // değiştirilebilir booking statuslarının kontorlünü yapan metot
  private void validateUpdatableStatus(BookingStatus targetStatus) {
    if (UPDATABLE_BOOKING_STATUSES.contains(targetStatus)) {
      return;
    }

    throw new BusinessRuleException(
        "Booking status update supports only CANCELLED or CHECKED_IN");
  }

  // status geçiş kuralları: CANCELLED son durumdur, CHECKED_IN iptal edilemez
  private void validateStatusTransition(BookingStatus currentStatus, BookingStatus targetStatus) {
    if (currentStatus == BookingStatus.CANCELLED) {
      throw new BusinessRuleException("Cancelled bookings cannot change status");
    }

    if (currentStatus == BookingStatus.CHECKED_IN && targetStatus == BookingStatus.CANCELLED) {
      throw new BusinessRuleException("Checked-in bookings cannot be cancelled");
    }
  }

// eğer son 24 saat olmadan check-in yapılmak istenirse reddeden metot.
  private void validateCheckInWindow(Flight flight) {
    LocalDateTime now = LocalDateTime.now();
    boolean isWithinFinal24Hours = flight.getDepartureTime().isAfter(now)
        && !flight.getDepartureTime().isAfter(now.plusHours(24));

    if (!isWithinFinal24Hours) {
      throw new BusinessRuleException(
          "Check-in is allowed only within the final 24 hours before departure. flight id "
              + flight.getFlightId());
    }
  }


  // 24 saatten kısaysa ve booking statüs canclled'sa ceza uygulama metotu
  private void applyCancellationPenalty(Booking booking, BookingStatus targetStatus, Flight flight) {
    boolean isCancelled = targetStatus == BookingStatus.CANCELLED;
    boolean isWithin24Hours = !flight.getDepartureTime().isAfter(LocalDateTime.now().plusHours(24));
    boolean shouldApplyPenalty = isCancelled && isWithin24Hours;

    booking.setCancellationPenaltyApplied(shouldApplyPenalty);
    booking.setCancellationPenaltyAmount(
        shouldApplyPenalty ? CANCELLATION_PENALTY_FEE : BigDecimal.ZERO);
  }


  // son 24 saat kaldıysa check in yapmamızı sağlayan yapı
  private BookingStatus resolveEffectiveStatus(BookingStatus requestedStatus, Flight flight) {
    if (requestedStatus == BookingStatus.CANCELLED) {
      return requestedStatus;
    }

    LocalDateTime now = LocalDateTime.now();
    boolean isWithinFinal24Hours = flight.getDepartureTime().isAfter(now)
        && !flight.getDepartureTime().isAfter(now.plusHours(24));

    return isWithinFinal24Hours ? BookingStatus.CHECKED_IN : requestedStatus;
  }

}
