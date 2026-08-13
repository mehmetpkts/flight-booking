package com.example.flight_booking.service;

import com.example.flight_booking.entity.Booking;
import com.example.flight_booking.entity.Flight;
import com.example.flight_booking.entity.Passenger;
import com.example.flight_booking.enums.BookingStatus;
import com.example.flight_booking.repository.BookingRepository;
import com.example.flight_booking.repository.FlightRepository;
import com.example.flight_booking.repository.PassengerRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final PassengerRepository passengerRepository;
  private final FlightRepository flightRepository;

  public BookingService(BookingRepository bookingRepository,
      PassengerRepository passengerRepository,
      FlightRepository flightRepository) {
    this.bookingRepository = bookingRepository;
    this.passengerRepository = passengerRepository;
    this.flightRepository = flightRepository;
  }

  public Booking createBooking(Long passengerId, Long flightId, LocalDateTime bookingDate, BookingStatus status,
      String pnr) {
    Passenger passenger = passengerRepository.findById(passengerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Passenger not found with id " + passengerId));
    Flight flight = flightRepository.findById(flightId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + flightId));

    Booking booking = new Booking();
    booking.setPassenger(passenger);
    booking.setFlight(flight);
    booking.setBookingDate(bookingDate);
    booking.setStatus(status);
    booking.setPnr(pnr);
    return bookingRepository.save(booking);
  }

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  public Booking getBookingById(Long id) {
    return bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Booking not found with id " + id));
  }

  public Booking updateBooking(Long id, Long passengerId, Long flightId, LocalDateTime bookingDate,
      BookingStatus status, String pnr) {
    Booking booking = getBookingById(id);
    Passenger passenger = passengerRepository.findById(passengerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Passenger not found with id " + passengerId));
    Flight flight = flightRepository.findById(flightId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Flight not found with id " + flightId));

    booking.setPassenger(passenger);
    booking.setFlight(flight);
    booking.setBookingDate(bookingDate);
    booking.setStatus(status);
    booking.setPnr(pnr);
    return bookingRepository.save(booking);
  }

  public void deleteBooking(Long id) {
    Booking booking = getBookingById(id);
    bookingRepository.delete(booking);
  }

}
